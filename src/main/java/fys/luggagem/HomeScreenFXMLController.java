package fys.luggagem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javax.swing.Timer;

/**
 *
 * @author jordan
 */
public class HomeScreenFXMLController implements Initializable {

    private Timer t;
    private Data data = MainApp.getData();

    @FXML
    private StackPane workspace;

    @FXML
    private Label nameLabel;

    @FXML
    private void handleLogOutAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/FXMLDocument.fxml"));
    }

    @FXML
    private void handleGebruikersbeheerAction(ActionEvent event) throws IOException {
        workspace.getChildren().clear();
        Parent pane = loadFXMLFile("/fxml/AccountbeheerFXML.fxml");
        workspace.getChildren().add(pane);
    }

    @FXML
    private void handleBeschadigdeBagageAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/BeschadigdeBagageFXML.fxml"));
    }

    @FXML
    private void handleRapportageAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/RapportageFXML.fxml"));
    }

    @FXML
    private void handleDatabaseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/DatabaseFXML.fxml"));
    }

    @FXML
    private void handleVerlorenBagageAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/VerlorenBagageFXML.fxml"));
    }

    @FXML
    private void handleHomeAction(ActionEvent event) {
        workspace.getChildren().clear();
        Parent pane = loadFXMLFile("/fxml/HomeScreenContentFXML.fxml");
        workspace.getChildren().add(pane);
    }

    @FXML
    private void handleGevondenBagageAction(ActionEvent event) throws IOException {
        System.out.println("Registreer vermissing clicked!");
        workspace.getChildren().clear();
        Parent pane = loadFXMLFile("/fxml/GevondenBagageFXML.fxml");
        workspace.getChildren().add(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Parent pane = loadFXMLFile("/fxml/HomeScreenContentFXML.fxml");
        workspace.getChildren().add(pane);
        nameLabel.setText(data.getName());
    }
    
    public void initHomeScreen() {
        Parent pane = loadFXMLFile("/fxml/HomeScreenContentFXML.fxml");
        workspace.getChildren().add(pane);
    }

    private Parent loadFXMLFile(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(data.getResourceBundle());
            return loader.load(this.getClass().getResource(fxmlFileName));
        } catch (IOException ex) {
            System.out.println(ex.getClass().getName() + ": " + ex.getMessage());
            return null;
        }
    }
}
