package fys.luggagem;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author Mees Sour
 */
public class VerlorenBagageController implements Initializable {

    private Data data = MainApp.getData();

    @FXML
    private DatePicker datum;

    @FXML
    private TextField timeHour;

    @FXML
    private TextField timeMinute;

    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SimpleDateFormat timeFormatHour = new SimpleDateFormat("HH");
        timeHour.setText(timeFormatHour.format(new Date()));

        SimpleDateFormat timeFormatMinute = new SimpleDateFormat("mm");
        timeMinute.setText(timeFormatMinute.format(new Date()));
    }
}
