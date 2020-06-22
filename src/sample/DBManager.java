package sample;

import java.sql.*;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class DBManager {

    private Connection connection;
    private Timestamp timestamp;
    private Timestamp GMT;
    private int id;
    private String postLink;
    private String postTitle;
    private String postDescription;
    private String postDate;
    private int postNumber;
    private String videoLink;
    private String code;


    public DBManager(String postTitle, String postDescription, String postDate, int postNumber, String videoLink) throws Exception {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postDate = postDate;
        this.postNumber = postNumber;
        this.videoLink = videoLink;
        this.connection = setConnection();
        setTimestamp();
        setGMT();
        setPostLink();
        setCode();
        setId();
    }

    private Connection setConnection() throws Exception {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "#####";
        String username = "####";
        String password = "####";
        Class.forName(driver);
        Connection connection = (Connection) DriverManager.getConnection(url, username, password);
        return connection;
    }

    public Connection getConnection() {
        return connection;
    }

    private void setTimestamp() {
        long time = System.currentTimeMillis();
        timestamp = new Timestamp(time);
    }

    private void setGMT() {
        long time = System.currentTimeMillis();
        long duration = ((120 * 60) * 1000);
        GMT = new Timestamp(time);
        GMT.setTime(GMT.getTime() - duration);
    }

    public void setId() throws Exception {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM cb_posts ORDER BY id DESC LIMIT 0, 1;";
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            id = rs.getInt("id");
        } else {
            id = 0;
        }
}

    public int getId() {
        return id;
    }

    private void setPostLink() {
        String nfdNormalizedString = Normalizer.normalize(postTitle.replaceAll("[^\\p{L}\\p{Z}]", "").toLowerCase().replaceAll("\\s", "-"), Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        postLink = pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    private void setCode() {
        code = "<div style=\"left: 7pt; top: 15pt; position: absolute; font-family: Tahoma, Arial, Helvetica, Sans-Serif; font-weight: bold; font-size:24px; padding: 3px; letter-spacing: 1px;\">[" + postNumber
                + "]</div>" + "<table>" +
                "<tr>" + "<td><center><font size=\"5\"> " + postDate + "</font></center><iframe width=\"450\" height=\"360\" src=\"https://www.youtube.com/embed/" + videoLink.substring(32) + "\" frameborder=\"0\" gesture=\"media\" allowfullscreen></iframe></td>"
                + "<td><BR>" + "&nbsp;&nbsp;&nbsp;&nbsp;" + postDescription + "<center><a href=\"https://www.youtube.com/watch?v=" + videoLink.substring(32) + "\">Oglądaj cały film ...</a></center>" +
                "</td>\r\n" +
                "</tr>\r\n" +
                "</table>";
    }

    public void setValues() throws Exception {

        String query = " insert into cb_posts (`ID`, `post_author`, `post_date`, `post_date_gmt`, `post_content`, `post_title`, `post_excerpt`, `post_status`, `comment_status`, `ping_status`, `post_password`, `post_name`, `to_ping`, `pinged`, `post_modified`, `post_modified_gmt`, `post_content_filtered`, `post_parent`, `guid`, `menu_order`, `post_type`, `post_mime_type`, `comment_count`)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        java.sql.PreparedStatement preparedStmt = connection.prepareStatement(query);

        preparedStmt.setInt(1, id + 1);
        preparedStmt.setBoolean(2, true);
        preparedStmt.setTimestamp(3, timestamp);
        preparedStmt.setTimestamp(4, GMT);
        preparedStmt.setString(5, code);
        preparedStmt.setString(6, postTitle);
        preparedStmt.setString(7, "");
        preparedStmt.setString(8, "publish");
        preparedStmt.setString(9, "open");
        preparedStmt.setString(10, "open");
        preparedStmt.setString(11, "");
        preparedStmt.setString(12, postLink);
        preparedStmt.setString(13, "");
        preparedStmt.setString(14, "");
        preparedStmt.setTimestamp(15, timestamp);
        preparedStmt.setTimestamp(16, GMT);
        preparedStmt.setString(17, "");
        preparedStmt.setInt(18, 0);
        preparedStmt.setString(19, "http://telewizjatychy.pl/?p=" + id + 1);
        preparedStmt.setInt(20, 0);
        preparedStmt.setString(21, "post");
        preparedStmt.setString(22, "");
        preparedStmt.setInt(23, 0);
        preparedStmt.execute();
    }



}
