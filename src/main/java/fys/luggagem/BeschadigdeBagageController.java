package fys.luggagem;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BeschadigdeBagageController implements Initializable {

    private Data data = MainApp.getData();
    private String imageURL;

    @FXML
    private ImageView image01;

    @FXML
    private ImageView image02;

    @FXML
    private ImageView image03;

    @FXML
    private Button saveImages;

    @FXML
    private Label savedConfirmation;

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

    @FXML
    private void loadImage01(ActionEvent event) throws MalformedURLException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteer de foto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            imageURL = file.toURI().toURL().toString();

            Image image = new Image(imageURL, 384, 205, false, false);
            image01.setImage(image);
        }
    }

    @FXML
    private void loadImage02(ActionEvent event) throws MalformedURLException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteer de foto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            imageURL = file.toURI().toURL().toString();

            Image image = new Image(imageURL, 384, 205, false, false);
            image02.setImage(image);
        }
    }

    @FXML
    private void loadImage03(ActionEvent event) throws MalformedURLException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteer de foto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            imageURL = file.toURI().toURL().toString();

            Image image = new Image(imageURL, 384, 205, false, false);
            image03.setImage(image);
        }
    }

    @FXML
    private void saveToDatabase(ActionEvent event) {
        //alert dialog
        imageURL = this.getClass().getResource("/images/corendon.jpg").toString();
        Image image = new Image(imageURL, 180, 50, false, false);
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(data.getStage());
        alert.setGraphic(new ImageView(image));
        alert.setTitle("Damaged luggage");
        alert.setHeaderText("Save and upload to database");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //set saved succesfull label visible for 5 seconds and disable save button
            saveImages.setVisible(false);
            savedConfirmation.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    savedConfirmation.setVisible(false);
                    saveImages.setVisible(true);
                    saveImages.setDisable(true);
                }
            });
            pause.play();
            
            //ToDo: INSERT input fields + images INTO database
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
