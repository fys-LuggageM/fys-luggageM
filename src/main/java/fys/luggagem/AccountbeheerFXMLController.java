package fys.luggagem;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.sql.Connection;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private TextField createUserBetweenName;
    @FXML
    private TextField createUserLastname;
    @FXML
    private ComboBox<String> airportBox;

    private ObservableList<Users> userTable = FXCollections.observableArrayList();
    ;

    private ObservableList<String> Airports
            = FXCollections.observableArrayList(
                    "AMS",
                    "TRK"
            );
    @FXML
    private TableView TableViewUsers;
    @FXML
    private TableColumn<?, ?> username;
    @FXML
    private TableColumn<?, ?> firstName;
    @FXML
    private TableColumn<?, ?> preposition;
    @FXML
    private TableColumn<?, ?> lastName;
    @FXML
    private TableColumn<?, ?> airport;
    @FXML
    private TableColumn<?, ?> permissions;
    @FXML
    private TableColumn<?, ?> active;
    @FXML
    private TextField createUserEmail;

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
        if ((createUserPassword.getText() == null || createUserPassword.getText().trim().isEmpty())
                || (createUsername.getText() == null || createUsername.getText().trim().isEmpty())
                || (createUserRealname.getText() == null || createUserRealname.getText().trim().isEmpty())
                || (createUserLastname.getText() == null || createUserLastname.getText().trim().isEmpty())
                || (createUserEmail.getText() == null || createUserEmail.getText().trim().isEmpty())) {
            createUserInfo.setTextFill(Paint.valueOf("d81e05"));
            createUserInfo.setFont(Font.font(10));
            createUserInfo.setText(data.getResourceBundle().getString("accountNotCreatedInfo"));
        } else {
            String createUserStringName = createUsername.getText();
            String createUserStringRealName = createUserRealname.getText();
            String betweenName = "";
            if (createUserBetweenName.getText() != null || !createUserBetweenName.getText().trim().isEmpty()) {
                betweenName = createUserBetweenName.getText();
            }
            String createUserStringEmail = createUserEmail.getText();
            String createUserStringLastName = createUserLastname.getText();
            String createUserStringAirport = "AMS";
            String password = createUserPassword.getText();

            String[] hashAndPass;
            hashAndPass = Encryptor.encrypt(password);

            int createUserIntPermissions;

            // Store universally named userPermissions 
            RadioButton selectedPermissions = (RadioButton) accountButtons.getSelectedToggle();
            String un18perms = selectedPermissions.getId();
            switch (un18perms) {
                case "roleManager":
                    createUserIntPermissions = 2;
                    break;
                case "roleAdmin":
                    createUserIntPermissions = 3;
                    break;
                default:
                    createUserIntPermissions = 1;
                    break;
            }

            String queryEmployee = "INSERT INTO `luggagem`.`employee` "
                    + "(`code`, `first_name`, `preposition`, `last_name`, `Luchthaven_IATA`) "
                    + "VALUES "
                    + "('" + createUserStringName +"', '" + createUserStringRealName + "', '" + betweenName + "', '" + createUserStringLastName + "', '" + createUserStringAirport + "');";
            int doQueryEmployeeTable = MainApp.myJDBC.executeUpdateQuery(queryEmployee);
            String queryAccounts = String.format("INSERT INTO `luggagem`.`account` "
                    + "(`Employee_code`, `email`, `password`, `salt`, `user_level`, `active`) "
                    + "VALUES "
                    + "('%s', '%s', '%s', '%s', '%d', '1');",
                    createUserStringEmail, createUserStringEmail, hashAndPass[0], hashAndPass[1], createUserIntPermissions);
            int doQueryAccountsTable = MainApp.myJDBC.executeUpdateQuery(queryAccounts);
            if (doQueryEmployeeTable == -1 || doQueryAccountsTable == -1) {
                createUserInfo.setTextFill(Paint.valueOf("d81e05"));
                createUserInfo.setFont(Font.font(10));
                createUserInfo.setText(data.getResourceBundle().getString("Something went wrong in the SQL query.\n Please contact your local sysadmin."));
            }
        }

    }

    @FXML
    private void deactivateUser(ActionEvent event
    ) {
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
        String query = "SELECT MAX(code)"
                + "FROM employee";
        String result = MainApp.myJDBC.executeStringQuery(query);
        result = Integer.toString(Integer.parseInt(result) + 1);
        createUsername.setText(result);
    }

    private void setupTableView() {
        try {
            String query = "SELECT Employee_code,first_name,preposition,last_name,user_level,Luchthaven_IATA,active FROM luggagem.account,luggagem.employee;";
            ResultSet result = MainApp.myJDBC.executeResultSetQuery(query);

            for (int cnr = 0; cnr < TableViewUsers.getColumns().size(); cnr++) {
                TableColumn tc = (TableColumn) TableViewUsers.getColumns().get(cnr);
                String propertyName = tc.getId();
                if (propertyName != null && !propertyName.isEmpty()) {
                    tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                    System.out.printf("Attached column %s in tableview to matching attribute.", propertyName);
                }
            }

            while (result.next()) {
                String TBusername = result.getString("Employee_Code");
                String TBfirstName = result.getString("first_name");
                String TBpreposition = null;
                String TBlastName = result.getString("last_name");
                int TBpermissions = result.getInt("user_level");
                String TBIATA = result.getString("Luchthaven_IATA");
                int TBactive = result.getInt("active");
                String realPermissions;
                String RealActive;
                if (TBactive == 0) {
                    RealActive = "Inactive";
                } else {
                    RealActive = "Active";
                }
                switch (TBpermissions) {
                    case 0:
                        realPermissions = "Employee";
                        break;
                    case 1:
                        realPermissions = "Manager";
                        break;
                    case 2:
                        realPermissions = "Admin";
                        break;
                    default:
                        realPermissions = "Employee";
                        break;
                }

                if (!result.getString("preposition").isEmpty() && result.getString("preposition") != null) {
                    TBpreposition = result.getString("preposition");
                }
                Users storingUsers = new Users(TBusername, TBfirstName, TBlastName, TBIATA, RealActive, realPermissions);

                if (TBpreposition != null && !TBpreposition.isEmpty()) {
                    storingUsers.setPreposition(TBpreposition);
                }
                userTable.add(storingUsers);
            }

            TableViewUsers.setItems(userTable);

        } catch (SQLException ex) {
            Logger.getLogger(AccountbeheerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set toggleGroup for roleAdmin
        roleAdmin.setToggleGroup(accountButtons);
        roleEmployee.setToggleGroup(accountButtons);
        roleManager.setToggleGroup(accountButtons);
        roleEmployee.setSelected(true);

        getNextStaffID();
        setupTableView();

        //
        airportBox.setItems(Airports);
    }
}
