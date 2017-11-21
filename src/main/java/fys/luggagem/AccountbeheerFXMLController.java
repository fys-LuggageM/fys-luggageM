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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class AccountbeheerFXMLController implements Initializable {

    Data data;

    @FXML
    private Label label;

    @FXML
    private TextField resetUser;

    @FXML
    private TextField resetPassword;

    @FXML
    private Label resetPasswordInfo;

    @FXML
    private TextField createUsername;

    @FXML
    private TextField createUserRealname;

    @FXML
    private TextField createUserPassword;

    @FXML
    private Label createUserInfo;

    @FXML
    private TextField deactivateUsername;

    @FXML
    private Label deactivateUserInfo;

    @FXML
    private RadioButton roleAdmin;

    @FXML
    private RadioButton roleManager;

    @FXML
    private RadioButton roleEmployee;

    @FXML
    private final ToggleGroup accountButtons = new ToggleGroup();

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
        if ((resetUser.getText() == null || resetUser.getText().trim().isEmpty()) || (resetPassword.getText().trim().isEmpty())) {
            resetPasswordInfo.setTextFill(Paint.valueOf("d81e05"));
            resetPasswordInfo.setText("Wachtwoord niet gereset. Een of meerdere velden zijn leeg.");
        } else {
            resetPasswordInfo.setTextFill(Paint.valueOf("green"));
            resetPasswordInfo.setText("Wachtwoord gereset.");
            resetUser.clear();
            resetPassword.clear();
        }
    }

    @FXML
    private void createAccount(ActionEvent event) {
        System.out.println("Account aangemaakt.");
        if ((createUserPassword.getText() == null || createUserPassword.getText().trim().isEmpty()) || (createUsername.getText() == null || createUsername.getText().trim().isEmpty()) || (createUserRealname.getText() == null || createUserRealname.getText().trim().isEmpty())) {
            createUserInfo.setTextFill(Paint.valueOf("d81e05"));
            createUserInfo.setFont(Font.font(10));
            createUserInfo.setText("Account niet aangemaakt. Een of meerdere velden zijn leeg.");
        } else {
            createUserInfo.setTextFill(Paint.valueOf("green"));
            createUserInfo.setFont(Font.font(12));
            createUserInfo.setText("Account Aangemaakt.");
            // Empty everything
            createUserPassword.clear();
            createUserRealname.clear();
            createUsername.clear();
            roleAdmin.setSelected(false);
            roleManager.setSelected(false);
            roleEmployee.setSelected(false);
        }
    }

    @FXML
    private void deactivateUser(ActionEvent event) {
        System.out.println("Gebruiker ge(de)activeerd.");
        if (deactivateUsername.getText() == null || deactivateUsername.getText().trim().isEmpty()) {
            deactivateUsername.clear();
            deactivateUserInfo.setTextFill(Paint.valueOf("d81e05"));
            deactivateUserInfo.setText("Voer een gebruiker in.");
        } else {
            deactivateUserInfo.setTextFill(Paint.valueOf("green"));
            deactivateUserInfo.setText("Gebruiker ge(de)activeerd.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleAdmin.setToggleGroup(accountButtons);
        roleEmployee.setToggleGroup(accountButtons);
        roleManager.setToggleGroup(accountButtons);
    }

    public void initData(Data mainData) {
        data = mainData;
    }
}
