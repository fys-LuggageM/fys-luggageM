package fys.luggagem;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author Mees Sour
 */
public class GevondenBagageController implements Initializable {
    
    private Data data = MainApp.getData();

    @FXML
    private Label statusMessage;

    @FXML
    private TextField datumDag;

    @FXML
    private TextField datumMaand;

    @FXML
    private TextField datumJaar;

    @FXML
    private TextField tijdUur;

    @FXML
    private TextField tijdMinuut;

    @FXML
    private TextField labelNummer;

    @FXML
    private TextField vluchtNummer;

    @FXML
    private TextField bestemming;

    @FXML
    private TextField voorNaam;

    @FXML
    private TextField tussenvoegsel;

    @FXML
    private TextField achterNaam;

    @FXML
    private TextField luchthaven;

    @FXML
    private TextField lostAndFoundId;

    @FXML
    private TextField bagageType;

    @FXML
    private TextField bagageMerk;

    @FXML
    private TextField bagageKleur;

    @FXML
    private TextField bagageBijzondereKenmerken;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label nameLabel;

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

    @FXML
    private void opslaan(ActionEvent event) {
        statusMessage.setText("Succesvol opgeslagen naar database");
        System.out.println("GELUKT!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SimpleDateFormat dateFormatDag = new SimpleDateFormat("dd");
        datumDag.setText(dateFormatDag.format(new Date()));
        SimpleDateFormat dateFormatMaand = new SimpleDateFormat("MM");
        datumMaand.setText(dateFormatMaand.format(new Date()));
        SimpleDateFormat dateFormatJaar = new SimpleDateFormat("yyyy");
        datumJaar.setText(dateFormatJaar.format(new Date()));

        SimpleDateFormat timeFormatUur = new SimpleDateFormat("HH");
        tijdUur.setText(timeFormatUur.format(new Date()));
        SimpleDateFormat timeFormatMinuut = new SimpleDateFormat("mm");
        tijdMinuut.setText(timeFormatMinuut.format(new Date()));
    }

}
