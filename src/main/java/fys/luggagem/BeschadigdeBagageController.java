package fys.luggagem;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BeschadigdeBagageController implements Initializable {

    Data data;

    @FXML
    private ImageView image01;

    @FXML
    private ImageView image02;

    @FXML
    private ImageView image03;

    private String imageURL;

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
        Parent root = (Parent) loader.load();
        HomeScreenFXMLController controller = loader.getController();
        controller.initData(data);
        data.getScene().setRoot(root);
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

            Image image = new Image(imageURL, 384, 216, false, false);
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

            Image image = new Image(imageURL, 384, 216, false, false);
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

            Image image = new Image(imageURL, 384, 216, false, false);
            image03.setImage(image);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initData(Data mainData) {
        data = mainData;
    }
}
