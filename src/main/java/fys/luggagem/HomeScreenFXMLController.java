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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javax.swing.Timer;

/**
 *
 * @author jordan
 */
public class HomeScreenFXMLController implements Initializable {

    private Timer t;   
    String name;

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
        Scene window = Data.getScene();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/FXMLDocument.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.Bundle"));
        Parent root = (Parent) loader.load();
        FXMLDocumentController controller = loader.getController();
        Data.getScene().setRoot(root);
    }

    @FXML
    private void handleGebruikersbeheerAction(ActionEvent event) throws IOException {
        t.stop();
        Scene window = Data.getScene();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AccountbeheerFXML.fxml"));
        Parent root = (Parent) loader.load();
        AccountbeheerFXMLController controller = loader.getController();
        Data.getScene().setRoot(root);
    }

    @FXML
    private void handleBeschadigdeBagageAction(ActionEvent event) throws IOException {
        t.stop();
        Scene window = Data.getScene();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/BeschadigdeBagageFXML.fxml"));
        Parent root = (Parent) loader.load();
        BeschadigdeBagageController controller = loader.getController();
        Data.getScene().setRoot(root);
    }

    @FXML
    private void handleRapportageAction(ActionEvent event) throws IOException {
        t.stop();
        Scene window = Data.getScene();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/RapportageFXML.fxml"));
        Parent root = (Parent) loader.load();
        RapportageController controller = loader.getController();
        Data.getScene().setRoot(root);
    }
    
    @FXML
    private void handleDatabaseAction(ActionEvent event) throws IOException {
        t.stop();
        Scene window = Data.getScene();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/DatabaseFXML.fxml"));
        Parent root = (Parent) loader.load();
        DatabaseController controller = loader.getController();
        Data.getScene().setRoot(root);
    }
    
    @FXML
    private void handleVerlorenBagageAction(ActionEvent event) throws IOException {
        t.stop();
        Scene window = Data.getScene();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/VerlorenBagageFXML.fxml"));
        Parent root = (Parent) loader.load();
        VerlorenBagageController controller = loader.getController();
        Data.getScene().setRoot(root);
    }
    
    @FXML
    private void handleGevondenBagageAction(ActionEvent event) throws IOException {
        t.stop();
        Scene window = Data.getScene();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/GevondenBagageFXML.fxml"));
        Parent root = (Parent) loader.load();
        GevondenBagageController controller = loader.getController();
        Data.getScene().setRoot(root);
    }

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
                            greetingLabel.setText("Goedennacht, " + Data.getName().substring(0, Data.name.indexOf(" ")));
                        } else if (hour >= 6 && hour < 12) {
                            greetingLabel.setText("Goedemorgen, " + Data.getName().substring(0, Data.name.indexOf(" ")));
                        } else if (hour >= 12 && hour < 18) {
                            greetingLabel.setText("Goedemiddag, " + Data.getName().substring(0, Data.name.indexOf(" ")));
                        } else if (hour >= 18 && hour < 24) {
                            greetingLabel.setText("Goedenavond, " + Data.getName().substring(0, Data.name.indexOf(" ")));
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
