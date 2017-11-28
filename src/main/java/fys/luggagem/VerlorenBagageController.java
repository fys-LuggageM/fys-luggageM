package fys.luggagem;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author Mees Sour
 */
public class VerlorenBagageController implements Initializable {

    private Data data = MainApp.getData();

    @FXML
    private TextField timeHour;
    @FXML
    private TextField timeMinute;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField registrationNumber;
    @FXML
    private DatePicker date;
    @FXML
    private TextField luggageType;
    @FXML
    private TextField brand;
    @FXML
    private TextField arrivedWithFlight;
    @FXML
    private TextField tag;
    @FXML
    private TextField locationFound;
    @FXML
    private TextField color1;
    @FXML
    private TextField color2;
    @FXML
    private TextField luggageSizeHeight;
    @FXML
    private TextField luggageSizeWidth;
    @FXML
    private TextField luggageSizeDepth;
    @FXML
    private TextField luggageWeight;
    @FXML
    private TextField name1;
    @FXML
    private TextField name2;
    @FXML
    private TextField name3;
    @FXML
    private TextField city;
    @FXML
    private TextArea comments;
    @FXML
    private Button save;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SimpleDateFormat timeFormatHour = new SimpleDateFormat("HH");
        timeHour.setText(timeFormatHour.format(new Date()));

        SimpleDateFormat timeFormatMinute = new SimpleDateFormat("mm");
        timeMinute.setText(timeFormatMinute.format(new Date()));
    }
}
