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
public class VerlorenBagageController implements Initializable {

    private Data data = MainApp.getData();

    @FXML
    private Label statusMessage;

    @FXML
    private TextField DatumDag;

    @FXML
    private TextField DatumMaand;

    @FXML
    private TextField DatumJaar;

    @FXML
    private TextField TijdUur;

    @FXML
    private TextField TijdMinuut;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void opslaan(ActionEvent event) {
        statusMessage.setText("Succesvol opgeslagen naar database");
        System.out.println("GELUKT!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SimpleDateFormat dateFormatDag = new SimpleDateFormat("dd");
        DatumDag.setText(dateFormatDag.format(new Date()));
        SimpleDateFormat dateFormatMaand = new SimpleDateFormat("MM");
        DatumMaand.setText(dateFormatMaand.format(new Date()));
        SimpleDateFormat dateFormatJaar = new SimpleDateFormat("yyyy");
        DatumJaar.setText(dateFormatJaar.format(new Date()));

        SimpleDateFormat timeFormatUur = new SimpleDateFormat("HH");
        TijdUur.setText(timeFormatUur.format(new Date()));
        SimpleDateFormat timeFormatMinuut = new SimpleDateFormat("mm");
        TijdMinuut.setText(timeFormatMinuut.format(new Date()));
    }

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

}
