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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class AccountbeheerFXMLController implements Initializable {

    Data data;

    @FXML
    private Label label;

    @FXML
    private TextField resetUser;

    @FXML
    private TextField resetPassword;

    @FXML
    private TextField createUsername;

    @FXML
    private TextField createUserRealname;

    @FXML
    private TextField createUserPassword;

    @FXML
    private TextField deactivateUsername;

    @FXML
    private RadioButton roleAdmin;

    @FXML
    private RadioButton roleManager;

    @FXML
    private RadioButton roleEmployee;

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
        Parent root = (Parent) loader.load();
        HomeScreenFXMLController controller = loader.getController();
        controller.initData(data);
        data.getScene().setRoot(root);
    }

    @FXML
    private void resetPasswordAction(ActionEvent event) {
        System.out.println("Wachtwoord gereset.");
        resetUser.clear();
        resetPassword.clear();
    }

    @FXML
    private void createAccount(ActionEvent event) {
        System.out.println("Account aangemaakt.");
        createUserPassword.clear();
        createUserRealname.clear();
        createUsername.clear();
        roleAdmin.setSelected(false);
        roleManager.setSelected(false);
        roleEmployee.setSelected(false);
    }

    @FXML
    private void deactivateUser(ActionEvent event) {
        System.out.println("Gebruiker ge(de)activeerd.");
        deactivateUsername.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initData(Data mainData) {
        data = mainData;
    }
}
