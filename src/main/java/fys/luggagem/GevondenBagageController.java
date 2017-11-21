package fys.luggagem;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author Mees Sour
 */
public class GevondenBagageController implements Initializable {

    Data data;

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
    private Label nameLabel;

    @FXML
    private void handleSaveAction(ActionEvent event) {
        statusMessage.setText("Succesvol opgeslagen naar database");
        System.out.println("GELUKT!");
    }

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
        Parent root = (Parent) loader.load();
        HomeScreenFXMLController controller = loader.getController();
        controller.initData(data);
        data.getScene().setRoot(root);
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

    public void initData(Data mainData) {
        data = mainData;
    }
}
