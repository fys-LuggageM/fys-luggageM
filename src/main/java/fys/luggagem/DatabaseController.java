package fys.luggagem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DatabaseController implements Initializable {
    
    private Data data = MainApp.getData();
    
    @FXML
    private Label label;
    @FXML
    private Label nameLabel;
    @FXML
    private Button Button;
    @FXML
    private Button verlorenBagageButton;
    @FXML
    private Button gevondenBagageButton;

    @FXML
    private void handleBeschadigdeButtonAction(ActionEvent event) {
        System.out.println("Beschadigde bagage");
        label.setText("data van Beschadigde Bagage");
    }

    @FXML
    private void handleVerlorenButtonAction(ActionEvent event) {
        System.out.println("Verloren bagage");
        label.setText("data van Verloren Bagage");
    }

    @FXML
    private void handleGevondenButtonAction(ActionEvent event) {
        System.out.println("Gevonden bagage");
        label.setText("data van Gevonden Bagage");
    }
    
    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
