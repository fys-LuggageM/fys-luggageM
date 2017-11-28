package fys.luggagem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
            resetPasswordInfo.setText(data.getResourceBundle().getString("passwordNotResetInfo"));
        } else {
            String userToReset = resetUser.getText();
            String newPassword = resetPassword.getText();

            String[] hashAndPass;
            hashAndPass = Encryptor.encrypt(newPassword);

            String query = String.format("UPDATE `luggagem`.`staffmembers` "
                    + "SET `password`='%s', `salt`='%s' "
                    + "WHERE `staffID`='%s';", hashAndPass[0], hashAndPass[1], userToReset);
            MainApp.myJDBC.executeUpdateQuery(query);

            resetPasswordInfo.setTextFill(Paint.valueOf("green"));
            resetPasswordInfo.setText(data.getResourceBundle().getString("passwordResetInfo"));
            resetUser.clear();
            resetPassword.clear();
        }
    }

    @FXML
    private void createAccount(ActionEvent event) {
        System.out.println("Account aangemaakt.");
        if ((createUserPassword.getText() == null || createUserPassword.getText().trim().isEmpty())
                || (createUsername.getText() == null || createUsername.getText().trim().isEmpty())
                || (createUserRealname.getText() == null || createUserRealname.getText().trim().isEmpty())) {
            createUserInfo.setTextFill(Paint.valueOf("d81e05"));
            createUserInfo.setFont(Font.font(10));
            createUserInfo.setText(data.getResourceBundle().getString("accountNotCreatedInfo"));
        } else {
            // Store username, password, permissions and real name in variables.
            String username = createUsername.getText();
            String realName = createUserRealname.getText();
            String password = createUserPassword.getText();
            String permissions;

            // Store universally named userPermissions
            RadioButton selectedPermissions = (RadioButton) accountButtons.getSelectedToggle();
            String un18perms = selectedPermissions.getId();
            switch (un18perms) {
                case "roleEmployee":
                    permissions = "Employee";
                    break;
                case "roleManager":
                    permissions = "Manager";
                    break;
                case "roleAdmin":
                    permissions = "Admin";
                    break;
                default:
                    createUserInfo.setText("Something went really wrong...");
                    return;
            }

            String[] hashAndPass;
            hashAndPass = Encryptor.encrypt(password);

            //TODO: Write SQL query
            String query = "INSERT INTO `luggagem`.`staffmembers` "
                    + "(`staffID`, `firstName`, `lastName`, `email`, `password`, `salt`) "
                    + "VALUES "
                    + "('" + username + "', '" + realName + "', '" + permissions + "', '" + username  + "', '" + hashAndPass[0] + "', '" + hashAndPass[1] + "');";
            MainApp.myJDBC.executeUpdateQuery(query);
//            query = String.format("INSERT INTO `luggagem`.`staffmembers` "
//                    + "(`staffID`, `firstName`, `lastName`, `email`, `password`, `salt`) "
//                    + "VALUES "
//                    + "('2', 'Valentijn', 'Vermeij', 'vverm', 'test', 'salty');", 
//                       username, realName, permissions, hashAndPass[0], hashAndPass[1] );
//            // Confirm to the user that the account was succesfully created
            System.out.println(query);
            createUserInfo.setTextFill(Paint.valueOf("green"));
            createUserInfo.setFont(Font.font(12));
            createUserInfo.setText(data.getResourceBundle().getString("accountCreatedInfo"));

            // Empty everything
            createUserPassword.clear();
            createUserRealname.clear();

            getNextStaffID();

            roleAdmin.setSelected(false);
            roleManager.setSelected(false);
            roleEmployee.setSelected(true);
        }
    }

    @FXML
    private void deactivateUser(ActionEvent event) {
        System.out.println("Gebruiker ge(de)activeerd.");
        if (deactivateUsername.getText() == null || deactivateUsername.getText().trim().isEmpty()) {
            deactivateUsername.clear();
            deactivateUserInfo.setTextFill(Paint.valueOf("d81e05"));
            deactivateUserInfo.setText(data.getResourceBundle().getString("accountNotDeactivatedInfo"));
        } else {
            String userToDeactivate = deactivateUsername.getText();

            //TODO: Write SQL Query
            deactivateUserInfo.setTextFill(Paint.valueOf("green"));
            deactivateUserInfo.setText(data.getResourceBundle().getString("accountDeactivatedInfo"));
        }
    }

    private void getNextStaffID() {
        // Get next staff ID
        String query = "SELECT MAX(staffid)"
                + "FROM staffmembers";
        String result = MainApp.myJDBC.executeStringQuery(query);
        result = Integer.toString(Integer.parseInt(result) + 1);
        createUsername.setText(result);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set toggleGroup for roleAdmin
        roleAdmin.setToggleGroup(accountButtons);
        roleEmployee.setToggleGroup(accountButtons);
        roleManager.setToggleGroup(accountButtons);
        roleEmployee.setSelected(true);

        getNextStaffID();
    }
}
