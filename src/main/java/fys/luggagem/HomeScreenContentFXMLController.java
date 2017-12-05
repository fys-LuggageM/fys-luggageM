package fys.luggagem;

import fys.luggagem.models.Data;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.swing.Timer;

/**
 * FXML Controller class
 *
 * @author jordan
 */
public class HomeScreenContentFXMLController implements Initializable {

    Data data = MainApp.getData();
    Timer t;
    
    @FXML
    private Label timeLabel;
    
    @FXML
    private Label greetingLabel;
    
    @FXML
    private Label dateLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
