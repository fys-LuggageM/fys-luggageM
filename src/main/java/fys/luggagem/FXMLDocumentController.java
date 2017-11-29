package fys.luggagem;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author jordan
 */
public class FXMLDocumentController implements Initializable {

    private Stage prevStage;
//    private boolean loginSuccesvol = false;
//    private int index = 0;
//    private final String[] usernames = {"Jordan van Beijnhem", "Tabish Nanhekhan", "Joris Ebbelaar",
//        "Valentijn Vermeij", "Pathe Dude", "Ayoub El Gris"};
    private Data data = MainApp.getData();
    Scene window;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label logInLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button logInButton;

    @FXML
    private Button dutchButton;

    @FXML
    private Button englishButton;

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
        userCheck(name, pass);
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleEnglishAction(ActionEvent event) {
        if (!data.getLanguage().equals("en") && !data.getCountry().equals("US")) {
            data.setLanguage("en");
            data.setCountry("US");
            data.setLocale();
            dutchButton.setStyle("-fx-background-color: white");
            englishButton.setStyle("-fx-background-color: grey");
            refreshText();
        }
    }

    @FXML
    private void handleDutchAction(ActionEvent event) {
        if (!data.getLanguage().equals("nl") && !data.getCountry().equals("NL")) {
            data.setLanguage("nl");
            data.setCountry("NL");
            data.setLocale();
            dutchButton.setStyle("-fx-background-color: grey");
            englishButton.setStyle("-fx-background-color: white");
            refreshText();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (data.getLanguage().equals("en") && data.getCountry().equals("US")) {
            dutchButton.setStyle("-fx-background-color: white");
            englishButton.setStyle("-fx-background-color: grey");
        } else {
            dutchButton.setStyle("-fx-background-color: grey");
            englishButton.setStyle("-fx-background-color: white");
        }

        // Set application icon
        // Get stage
        Stage stage = (data.getStage());
        // Add a custom icon
        stage.getIcons().add(new Image(this.getClass().getResource("/images/corendon_star_logo.png").toString()));
    }

//    private void userCheck(String username, String password, ActionEvent event) throws IOException {
//        while (!loginSuccesvol && index < usernames.length) {
//            if (username.equals(usernames[index]) && password.equals("test")) {
//                loginSuccesvol = true;
//                data.setName(usernames[index]);
//                MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
//            }
//            index++;
//        }
//        if (!loginSuccesvol) {
//            statusMessage.setText("De ingevulde gegevens zijn niet correct!");
//            System.out.println("MISLUKT!");
//        }
//        index = 0;
//        loginSuccesvol = false;
//    }
    private void userCheck(String username, String password) throws IOException {
        try {
            ResultSet resultSet = MainApp.myJDBC.executeResultSetQuery("SELECT * FROM staffMembers");
            while (resultSet.next()) {
                if (username.equals(resultSet.getString("email")) && Encryptor.encrypt(password,
                        resultSet.getString("salt")).equals(resultSet.getString("password"))) {
                    data.setName(resultSet.getString("firstName") + (resultSet.getString("preposition") != null ? " "
                            + resultSet.getString("preposition") : "") + " " + resultSet.getString("lastName"));
                    MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
                }
            }
            statusMessage.setText("De ingevulde gegevens zijn niet correct!");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void refreshText() {
        logInLabel.setText(data.getResourceBundle().getString("logIn"));
        logInButton.setText(data.getResourceBundle().getString("logIn"));
        exitButton.setText(data.getResourceBundle().getString("exit"));
        username.setPromptText(data.getResourceBundle().getString("username"));
        password.setPromptText(data.getResourceBundle().getString("password"));
    }
}
