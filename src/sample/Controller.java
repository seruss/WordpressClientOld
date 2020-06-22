package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private Text exitButton;
    @FXML
    private TreeView<String> categories = new TreeView<>();
    @FXML
    private TextField postDate;
    @FXML
    private TextField postNumber;
    @FXML
    private TextField postTitle;
    @FXML
    private TextField urlAddress;
    @FXML
    private TextArea postDescription;
    @FXML
    private Button applyButton;
    @FXML
    private Button checkButton;
    @FXML
    private ImageView thumbnail;
    @FXML
    private Button forward;
    @FXML
    private Button back;

    private int currentListPostion = 120;
    private ArrayList<String> videos;
    private ArrayList<String> titles;

    CheckBoxTreeItem<String> kategorie = new CheckBoxTreeItem<String>("Kategorie");
    CheckBoxTreeItem<String> zakazWstepu = new CheckBoxTreeItem<String>("ZAKAZ WSTĘPU!");
    CheckBoxTreeItem<String> wartoObejrzec = new CheckBoxTreeItem<String>("WARTO OBEJRZEĆ");
    CheckBoxTreeItem<String> wydarzenia = new CheckBoxTreeItem<String>("WYDARZENIA");
    CheckBoxTreeItem<String> kosciol = new CheckBoxTreeItem<String>("Kościół");
    CheckBoxTreeItem<String> kultura = new CheckBoxTreeItem<String>("Kulturalne");
    CheckBoxTreeItem<String> sportowe = new CheckBoxTreeItem<String>("Sportowe");
    CheckBoxTreeItem<String> gospodarcze = new CheckBoxTreeItem<String>("Gospodarcze");
    CheckBoxTreeItem<String> szkolyEdukacja = new CheckBoxTreeItem<String>("Szkoły/Edukacja");
    CheckBoxTreeItem<String> wypadki = new CheckBoxTreeItem<String>("Wypadki");
    CheckBoxTreeItem<String> archiwalne = new CheckBoxTreeItem<String>("Archiwalne");
    CheckBoxTreeItem<String> tyszanie = new CheckBoxTreeItem<String>("TYSZANIE");
    CheckBoxTreeItem<String> inicjatywy = new CheckBoxTreeItem<String>("Inicjatywy społeczne");
    CheckBoxTreeItem<String> ludzie = new CheckBoxTreeItem<String>("Ciekawi ludzie");
    CheckBoxTreeItem<String> stowarzyszenia = new CheckBoxTreeItem<String>("Stowarzyszenia");
    CheckBoxTreeItem<String> zabawne = new CheckBoxTreeItem<String>("ŚMIESZNE/ZABAWNE");
    CheckBoxTreeItem<String> adi = new CheckBoxTreeItem<String>("Adi dobrze Ci radzi!");
    CheckBoxTreeItem<String> tosiewytnie = new CheckBoxTreeItem<String>("ToSieWytnie...");

    private String query = " insert into cb_term_relationships (`object_id`, `term_taxonomy_id`, `term_order`)" + " values (?, ?, ?)";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DataCollector bundle = new DataCollector();
            videos = bundle.getList();
            titles = bundle.getTitles();
            urlAddress.setText(bundle.getVideoLink());
            postDate.setText(bundle.getDate());
            postNumber.setText(Integer.toString(bundle.getNumber()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DataCollector bundle = new DataCollector(urlAddress.getText());
                    postTitle.setText(bundle.getTitle());
                    postDescription.setText(bundle.getDescription());
                    postNumber.setText(Integer.toString(bundle.getNumber()));
                    thumbnail.setImage(bundle.getImage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentListPostion = currentListPostion+1;
                urlAddress.setText(videos.get(currentListPostion%30));
                postTitle.setText(titles.get(currentListPostion%30));
            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentListPostion = currentListPostion-1;
                urlAddress.setText(videos.get(currentListPostion%30));
                postTitle.setText(titles.get(currentListPostion%30));
            }
        });

        postDescription.setWrapText(true);

        kategorie.setExpanded(true);
        zakazWstepu.setExpanded(true);
        wartoObejrzec.setExpanded(true);
        wydarzenia.setExpanded(true);
        tyszanie.setExpanded(true);
        zabawne.setExpanded(true);
        wydarzenia.setSelected(true);

        wydarzenia.getChildren().addAll(kosciol, kultura, sportowe, gospodarcze, szkolyEdukacja, wypadki, archiwalne);
        tyszanie.getChildren().addAll(inicjatywy, ludzie, stowarzyszenia);
        zabawne.getChildren().addAll(adi, tosiewytnie);
        kategorie.getChildren().addAll(zakazWstepu, wartoObejrzec, wydarzenia, tyszanie, zabawne);

        zakazWstepu.setIndependent(true);
        wartoObejrzec.setIndependent(true);
        wydarzenia.setIndependent(true);
        tyszanie.setIndependent(true);
        zabawne.setIndependent(true);
        kosciol.setIndependent(true);
        kultura.setIndependent(true);
        sportowe.setIndependent(true);
        gospodarcze.setIndependent(true);
        szkolyEdukacja.setIndependent(true);
        wypadki.setIndependent(true);
        archiwalne.setIndependent(true);
        inicjatywy.setIndependent(true);
        ludzie.setIndependent(true);
        stowarzyszenia.setIndependent(true);
        adi.setIndependent(true);
        tosiewytnie.setIndependent(true);

        categories.setRoot(kategorie);
        categories.setShowRoot(false);
        categories.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

        window.setBackground(Background.EMPTY);

    }

    private void setTaxonomy(Connection connection, int id) throws Exception {

        PreparedStatement preparedStmt = connection.prepareStatement(query);

        if (zakazWstepu.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 1087);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (wartoObejrzec.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 1085);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (wydarzenia.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 10);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (tyszanie.isSelected()) {

            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 6);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (zabawne.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 251);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (kosciol.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 452);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (kultura.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 13);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (sportowe.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 14);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (gospodarcze.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 327);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (szkolyEdukacja.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 15);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (wypadki.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 16);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (archiwalne.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 11);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (inicjatywy.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 7);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (ludzie.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 8);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (stowarzyszenia.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 9);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (adi.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 1092);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        if (tosiewytnie.isSelected()) {
            preparedStmt.setInt(1, id);
            preparedStmt.setInt(2, 271);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
        }
        connection.close();
    }

    public void exit() {
        System.exit(0);
    }

    public void exitEntered() {
        exitButton.setFill(Color.web("4775d1"));
    }

    public void exitReleased() {
        exitButton.setFill(Color.web("000000"));
    }

    public void checkData() throws Exception {


    }

    public void publish() throws Exception {
        DBManager manager = new DBManager(postTitle.getText(), postDescription.getText(), postDate.getText(), Integer.parseInt(postNumber.getText()), urlAddress.getText());
        manager.setValues();
        manager.setId();
        setTaxonomy(manager.getConnection(), manager.getId());
    }

}
