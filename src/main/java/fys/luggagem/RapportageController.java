package fys.luggagem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author Tabish N
 */
public class RapportageController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button close;

    @FXML
    private Button button;

    @FXML
    private ComboBox<String> comboYear;

    @FXML
    private ComboBox<String> comboDay;

    @FXML
    private ComboBox<String> comboMonth;

    @FXML
    private TabPane tabel;

    @FXML
    private Tab tabVerloren;

    @FXML
    private Tab tabGevonden;

    @FXML
    private Tab tabBeschadigd;

    @FXML
    private Label tabLabelData;

    @FXML
    private Button exportButton;

    @FXML
    private Label exportLabel;

    @FXML
    private Label tabJoke;

    @FXML
    private Label tabJoke3;

    @FXML
    private Label tabLabelData3;

    @FXML
    private Label tabJoke2;

    @FXML
    private Label tabLabelData2;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Data geladen!");
        label.setText("Wil je data exporteren van " + comboDay.getValue() + " "
                + comboMonth.getValue() + " " + comboYear.getValue() + "?");

        tabJoke.setText("Hier komt iets");
        tabLabelData.setText("Hier komt ook iets");

        tabJoke2.setText("Hier komt iets");
        tabLabelData2.setText("Hier komt ook iets");

        tabJoke3.setText("Hier komt iets");
        tabLabelData3.setText("Hier komt ook iets");

    }

    @FXML
    private void exportButton(ActionEvent event) throws IOException {
        File file = MainApp.selectFileToSave("*.pdf");
        if (file != null) {
            String filename = file.getAbsolutePath();
            PDFExport.makePDF(filename);
            exportLabel.setText("Je export is opgeslagen in '" + filename + "'");
        } else {
            exportLabel.setText("Je export is geannuleerd");
        }

    }

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
        Parent root = (Parent) loader.load();
        HomeScreenFXMLController controller = loader.getController();
        Data.getScene().setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxController();
    }

    private void comboBoxController() {
        comboYear.getItems().addAll(
                "2000",
                "2001",
                "2002",
                "2003",
                "2004",
                "2005",
                "2006",
                "2007",
                "2008",
                "2009",
                "2010",
                "2011",
                "2012",
                "2013",
                "2014",
                "2015",
                "2016",
                "2017",
                "2018"
        );

        comboYear.getEditor().getText();

        comboMonth.getItems().addAll(
                "Januari",
                "Februari",
                "Maart",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Augustus",
                "September",
                "October",
                "November",
                "December"
        );
        comboMonth.getEditor().getText();

        comboDay.getItems().addAll(
                "1",
                "2",
                "10",
                "11",
                "20",
                "21",
                "30",
                "31"
        );
        comboDay.getEditor().getText();
    }
}
