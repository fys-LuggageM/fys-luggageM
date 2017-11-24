package fys.luggagem;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javax.swing.Timer;

/**
 *
 * @author jordan
 */
public class HomeScreenFXMLController implements Initializable {

    private Timer t;
    private Data data = MainApp.getData();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label timeLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label greetingLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private void handleLogOutAction(ActionEvent event) throws IOException {
        t.stop();
        MainApp.setScene(this.getClass().getResource("/fxml/FXMLDocument.fxml"));
    }

    @FXML
    private void handleGebruikersbeheerAction(ActionEvent event) throws IOException {
        t.stop();
        MainApp.setScene(this.getClass().getResource("/fxml/AccountbeheerFXML.fxml"));
    }

    @FXML
    private void handleBeschadigdeBagageAction(ActionEvent event) throws IOException {
        t.stop();
        MainApp.setScene(this.getClass().getResource("/fxml/BeschadigdeBagageFXML.fxml"));
    }

    @FXML
    private void handleRapportageAction(ActionEvent event) throws IOException {
        t.stop();
        MainApp.setScene(this.getClass().getResource("/fxml/RapportageFXML.fxml"));
    }

    @FXML
    private void handleDatabaseAction(ActionEvent event) throws IOException {
        t.stop();
        MainApp.setScene(this.getClass().getResource("/fxml/DatabaseFXML.fxml"));
    }

    @FXML
    private void handleVerlorenBagageAction(ActionEvent event) throws IOException {
        t.stop();
        MainApp.setScene(this.getClass().getResource("/fxml/VerlorenBagageFXML.fxml"));
    }

    @FXML
    private void handleGevondenBagageAction(ActionEvent event) throws IOException {
        t.stop();
        MainApp.setScene(this.getClass().getResource("/fxml/GevondenBagageFXML.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameLabel.setText(data.getName());
        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        timeLabel.setText(sdf.format(new Date()));
                        Calendar rightNow = Calendar.getInstance();
                        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                        if (hour >= 0 && hour < 6) {
                            greetingLabel.setText(data.getResourceBundle().getString("greetingsNight") + ", "
                                    + data.getName().substring(0, data.getName().indexOf(" ")));
                        } else if (hour >= 6 && hour < 12) {
                            greetingLabel.setText(data.getResourceBundle().getString("greetingsMorning") + ", "
                                    + data.getName().substring(0, data.getName().indexOf(" ")));
                        } else if (hour >= 12 && hour < 18) {
                            greetingLabel.setText(data.getResourceBundle().getString("greetingsAfternoon") + ", "
                                    + data.getName().substring(0, data.getName().indexOf(" ")));
                        } else if (hour >= 18 && hour < 24) {
                            greetingLabel.setText(data.getResourceBundle().getString("greetingsEvening") + ", "
                                    + data.getName().substring(0, data.getName().indexOf(" ")));
                        }
                    }
                });
            }
        });
        t.start();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateLabel.setText(dateFormat.format(new Date()));
    }
}
