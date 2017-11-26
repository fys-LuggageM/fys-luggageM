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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AccountbeheerFXMLController implements Initializable {

    private Data data = MainApp.getData();

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

    private final ToggleGroup accountButtons = new ToggleGroup();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private GridPane accountbeheer;

    @FXML
    private Label resetUserLabel;
    @FXML
    private Label resetPasswordLabel;
    @FXML
    private Text resetPasswordHeader;
    @FXML
    private Button resetPasswordButton;
    @FXML
    private Label createUsernameLabel;
    @FXML
    private Label createUserRealNameLabel;
    @FXML
    private Button createAccountButton;
    @FXML
    private Label createUserPasswordLabel;
    @FXML
    private Text createAccountHeader;
    @FXML
    private Label deactivateUserLabel;
    @FXML
    private Text deactivateUserHeader;
    @FXML
    private Button deactivateUserButton;
    @FXML
    private Text searchLabel;
    @FXML
    private TableColumn<?, ?> tableColumnUsername;
    @FXML
    private TableColumn<?, ?> tableColumnRealName;
    @FXML
    private TableColumn<?, ?> tableColumnPermissions;
    @FXML
    private TableColumn<?, ?> tableColumnActive;

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

    @FXML
    private void resetPasswordAction(ActionEvent event) {
        System.out.println("Wachtwoord gereset.");
        if ((resetUser.getText() == null || resetUser.getText().trim().isEmpty()) || (resetPassword.getText().trim().isEmpty())) {
            resetPasswordInfo.setTextFill(Paint.valueOf("d81e05"));
            resetPasswordInfo.setText("Wachtwoord niet gereset. Een of meerdere velden zijn leeg.");
        } else {
            String userToReset = resetUser.getText();
            String newPassword = resetPassword.getText();
            
            //TODO: Password hashing and write SQL query
            
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
            // Store username, password, permissions and real name in variables.
            String username = createUsername.getText();
            String realName = createUserRealname.getText();
            String password = createUserPassword.getText();
            String permissions;
            
            // Note: kinda ugly workaround for i18n, since getText returns incorrect
            // variables for internationalization. This if block basically checks
            // if the getText is equal to one of the three options and gives
            // permissions a reusable value to work with.
            RadioButton selectedPermissions = (RadioButton) accountButtons.getSelectedToggle();
            String un18perms = selectedPermissions.getText();
            if (un18perms.equals(data.getResourceBundle().getString("admin"))) {
                permissions = "admin";
            } else if (un18perms.equals(data.getResourceBundle().getString("manager"))){
                permissions = "manager";
            } else if (un18perms.equals(data.getResourceBundle().getString("deskEmployee"))){
                permissions = "employee";
            } else {
                createUserInfo.setText("Something went really wrong...");
            }
            
            //TODO: Hash password and write SQL query

            // Confirm to the user that the account was succesfully created
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
            String userToDeactivate = deactivateUsername.getText();
            
            //TODO: Write SQL Query
            
            deactivateUserInfo.setTextFill(Paint.valueOf("green"));
            deactivateUserInfo.setText("Gebruiker ge(de)activeerd.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set toggleGroup for roleAdmin
        roleAdmin.setToggleGroup(accountButtons);
        roleEmployee.setToggleGroup(accountButtons);
        roleManager.setToggleGroup(accountButtons);

        //i18n
        // RESET PASSWORD
        resetPasswordHeader.setText(data.getResourceBundle().getString("resetPassword"));
        resetUserLabel.setText(data.getResourceBundle().getString("username") + ":");
        resetPasswordLabel.setText(data.getResourceBundle().getString("newPassword") + ":");
        resetPasswordButton.setText(data.getResourceBundle().getString("resetPassword"));

        // CREATE ACCOUNT
        createAccountHeader.setText(data.getResourceBundle().getString("createAccount"));
        createUsernameLabel.setText(data.getResourceBundle().getString("username") + ":");
        createUserRealNameLabel.setText(data.getResourceBundle().getString("realName") + ":");
        createUserPasswordLabel.setText(data.getResourceBundle().getString("password") + ":");
        roleAdmin.setText(data.getResourceBundle().getString("admin"));
        roleEmployee.setText(data.getResourceBundle().getString("deskEmployee"));
        roleManager.setText(data.getResourceBundle().getString("manager"));
        createAccountButton.setText(data.getResourceBundle().getString("createAccount"));

        // DEACTIVATE ACCOUNT
        deactivateUserHeader.setText(data.getResourceBundle().getString("deactivateUser"));
        deactivateUserLabel.setText(data.getResourceBundle().getString("username") + ":");
        deactivateUserButton.setText(data.getResourceBundle().getString("deactivateUser"));

        // SEARCH ACCOUNT
        searchLabel.setText(data.getResourceBundle().getString("search"));
        tableColumnUsername.setText(data.getResourceBundle().getString("username"));
        tableColumnRealName.setText(data.getResourceBundle().getString("realName"));
        tableColumnPermissions.setText(data.getResourceBundle().getString("permissions"));
        tableColumnActive.setText(data.getResourceBundle().getString("active"));
    }
}
