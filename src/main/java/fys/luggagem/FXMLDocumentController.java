package fys.luggagem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author jordan
 */
public class FXMLDocumentController implements Initializable {

    private Stage prevStage;
    private boolean loginSuccesvol = false;
    private int index = 0;
    private final String[] usernames = {"Jordan van Beijnhem", "Tabish Nanhekhan", "Joris Ebbelaar", "Valentijn Vermeij", "Pathe Dude", "Ayoub El Gris"};
    Scene window;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Label statusMessage;

    @FXML
    private void handleLogInAction(ActionEvent event) throws IOException {
        String name = username.getText();
        String pass = password.getText();
        userCheck(name, pass, event);
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    private void userCheck(String username, String password, ActionEvent event) throws IOException {
        while (!loginSuccesvol && index < usernames.length) {
            if (username.equals(usernames[index]) && password.equals("test")) {
                loginSuccesvol = true;
                Data.setName(usernames[index]);
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
                Parent root = (Parent) loader.load();
                HomeScreenFXMLController controller = loader.getController();
                window = Data.getScene();
                window.setRoot(root);
            }
            index++;
        }
        if (!loginSuccesvol) {
            statusMessage.setText("De ingevulde gegevens zijn niet correct!");
            System.out.println("MISLUKT!");
        }
        index = 0;
        loginSuccesvol = false;
    }
}
