package sample;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataCollector {

    private String videoLink;
    private String date;
    private int number;
    private String description;
    private String title;
    private String youtubeWebContent;
    private Image image;
    private ArrayList<String> videos;
    private ArrayList<String> titles;

    public DataCollector() throws Exception {
        generateLists();
        setVideoLink();
        setDate();
        setNumber();
    }

    public DataCollector(String URL) throws Exception {
        youtubeWebContent = getURLSource(URL + "&amp;feature=applinks");
        setTitle();
        setDescription();
        setImage();
        setNumber();
    }

    private String getURLSource(String URL) throws Exception {
        URL link = new URL(URL);
        BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream(), StandardCharsets.UTF_8));
        StringBuilder webContent = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) webContent.append(line);
        in.close();
        return webContent.toString();
    }

    private void setVideoLink() throws Exception {
        videoLink = videos.get(0);
    }

    public String getVideoLink() {
        return videoLink;
    }

    private void setDate() {
        Date currentDate = new Date();
        SimpleDateFormat month = new SimpleDateFormat("MMMM");
        SimpleDateFormat year = new SimpleDateFormat("y");
        date = month.format(currentDate).substring(0, 1).toUpperCase() + month.format(currentDate).substring(1) + " " + year.format(currentDate);
    }

    public String getDate() {
        return date;
    }

    private void setNumber() throws Exception {
        String webContent = getURLSource("https://telewizjatychy.pl/");
        int start = webContent.indexOf("1px;\">");
        String number = webContent.substring(start + 7, start + 10);
        this.number = Integer.parseInt(number) + 1;
    }

    public int getNumber() {
        return number;
    }

    private void setTitle() {
        title = youtubeWebContent.substring(youtubeWebContent.indexOf("\\\"title\\\":\\\"") + 12, youtubeWebContent.indexOf("\\\",\\\"lengthSeconds\\")).replace("\\\\\\\"","\"");
    }

    public String getTitle() {
        return title;
    }

    private void setDescription() {
        description = youtubeWebContent.substring(youtubeWebContent.indexOf("\"shortDescription\\\":\\") + 22, youtubeWebContent.indexOf("\\\",\\\"isC")).replace("\\\\n", "<BR>").replace("https:\\/\\/patronite.pl\\/TelewizjaTychy", "<a href=\"https://patronite.pl/TelewizjaTychy\">patronite.pl/TelewizjaTychy</a>").replace("\\\\\\\"","\"");
    }

    public String getDescription() {
        return description;
    }

    private void setImage() {
        String link = youtubeWebContent.substring(youtubeWebContent.indexOf("og:image")+19, youtubeWebContent.indexOf("og:image:width")-22);
        image = new Image(link);
    }

    public Image getImage() {
        return image;
    }

    private void generateLists() throws Exception {
        videos = new ArrayList<>();
        titles = new ArrayList<>();
        String webContent = getURLSource("https://www.youtube.com/user/TelewizjaTychy/videos?disable_polymer=1");
        int index = webContent.indexOf("videos-u\" href=\"");
        while (index >= 0) {
           videos.add("https://www.youtube.com" + webContent.substring(index+16, index+36));
           titles.add(webContent.substring(index+53,index+50+webContent.substring(index+50).indexOf("</a>")));
           index = webContent.indexOf("videos-u\" href=\"", index + 2000);
        }
    }

    public ArrayList<String> getList() {
        return videos;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }
}
