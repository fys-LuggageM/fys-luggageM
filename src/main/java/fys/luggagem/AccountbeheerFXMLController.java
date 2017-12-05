package fys.luggagem;

import fys.luggagem.models.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.sql.ResultSet;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AccountbeheerFXMLController implements Initializable {

    private Data data = MainApp.getData();

    @FXML
    private TextField resetUser;

    @FXML
    private TextField resetPassword;

    @FXML
    private Label resetPasswordInfo;

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

    private ObservableList<User> userList = FXCollections.observableArrayList();

    private ObservableList<String> Airports
            = FXCollections.observableArrayList(
                    "AMS"
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
    @FXML
    private TextField searchField;

    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

    @FXML
    private void resetPasswordAction(ActionEvent event) {
        if ((resetUser.getText() == null || resetUser.getText().trim().isEmpty()) || (resetPassword.getText().trim().isEmpty())) {
            resetPasswordInfo.setTextFill(Paint.valueOf("d81e05"));
            resetPasswordInfo.setText(data.getResourceBundle().getString("passwordNotResetInfo"));
        } else {
            String userToReset = resetUser.getText();
            String newPassword = resetPassword.getText();

            String[] hashAndPass;
            hashAndPass = Encryptor.encrypt(newPassword);

            String imageURL = this.getClass().getResource("/images/32190-200.png").toString();
            Image image = new Image(imageURL, 64, 64, false, true);
            Alert resetPasswordAlert = new Alert(AlertType.CONFIRMATION);
            resetPasswordAlert.initOwner(data.getStage());
            resetPasswordAlert.setTitle("Reset Password");
            resetPasswordAlert.setContentText("Are you sure you want to reset the password for user " + userToReset);
            resetPasswordAlert.setGraphic(new ImageView(image));

            Optional<ButtonType> resetPasswordAnswer = resetPasswordAlert.showAndWait();
            if (resetPasswordAnswer.get() == ButtonType.OK) {
                String query = String.format("UPDATE `account` "
                        + "SET `password`='%s', `salt`='%s' "
                        + "WHERE `Employee_code`='%s';", hashAndPass[0], hashAndPass[1], userToReset);
                MainApp.myJDBC.executeUpdateQuery(query);

                resetPasswordInfo.setTextFill(Paint.valueOf("green"));
                resetPasswordInfo.setText(data.getResourceBundle().getString("passwordResetInfo"));
                resetUser.clear();
                resetPassword.clear();
            } else {

            }
        }
    }

    @FXML
    private void createAccount(ActionEvent event) {
        if ((createUserPassword.getText() == null || createUserPassword.getText().trim().isEmpty())
                || (createUserRealname.getText() == null || createUserRealname.getText().trim().isEmpty())
                || (createUserLastname.getText() == null || createUserLastname.getText().trim().isEmpty())
                || (createUserEmail.getText() == null || createUserEmail.getText().trim().isEmpty())
                || (airportBox.getSelectionModel().getSelectedItem() == null || airportBox.getSelectionModel().getSelectedItem().trim().isEmpty())) {
            createUserInfo.setTextFill(Paint.valueOf("d81e05"));
            createUserInfo.setFont(Font.font(10));
            createUserInfo.setText(data.getResourceBundle().getString("accountNotCreatedInfo"));
        } else {
            String createUserStringRealName = createUserRealname.getText();
            String betweenName = "";
            if (createUserBetweenName.getText() != null || !createUserBetweenName.getText().trim().isEmpty()) {
                betweenName = createUserBetweenName.getText();
            }
            String createUserStringEmail = createUserEmail.getText();
            String createUserStringLastName = createUserLastname.getText();
            String createUserStringAirport = airportBox.getSelectionModel().getSelectedItem();
            String password = createUserPassword.getText();

            String[] hashAndPass;
            hashAndPass = Encryptor.encrypt(password);

            String createUserStringPermissions;

            // Store universally named userPermissions 
            RadioButton selectedPermissions = (RadioButton) accountButtons.getSelectedToggle();
            String un18perms = selectedPermissions.getId();
            switch (un18perms) {
                case "roleManager":
                    createUserStringPermissions = "1";
                    break;
                case "roleAdmin":
                    createUserStringPermissions = "2";
                    break;
                default:
                    createUserStringPermissions = "0";
                    break;
            }

            Alert createUserAlert = new Alert(AlertType.CONFIRMATION);
            String imageURL = this.getClass().getResource("/images/2000px-New_user_icon-01.svg.png").toString();
            Image image = new Image(imageURL, 64, 64, false, true);

            createUserAlert.initOwner(data.getStage());
            createUserAlert.setTitle("Account creation");
            createUserAlert.setHeaderText("A new user will be created");
            createUserAlert.setContentText("A new user will be created with the following information:\n"
                    + "Please confirm the following information is correct\n"
                    + "Email: " + createUserStringEmail + "\n"
                    + "Airport: " + createUserStringAirport);
            createUserAlert.setGraphic(new ImageView(image));

            Optional<ButtonType> createUserAnswer = createUserAlert.showAndWait();
            if (createUserAnswer.get() == ButtonType.OK) {
                String createUserStringName = getNextStaffID();
                String queryEmployee = "INSERT INTO `employee` "
                        + "(`code`, `first_name`, `preposition`, `last_name`, `Luchthaven_IATA`) "
                        + "VALUES "
                        + "('" + createUserStringName + "', '" + createUserStringRealName + "', '" + betweenName + "', '" + createUserStringLastName + "', '" + createUserStringAirport + "');";
                int doQueryEmployeeTable = MainApp.myJDBC.executeUpdateQuery(queryEmployee);
                String queryAccounts = "INSERT INTO `account` "
                        + "(`Employee_code`, `email`, `password`, `salt`, `user_level`, `active`) "
                        + "VALUES "
                        + "('" + createUserStringName + "', '" + createUserStringEmail + "', '" + hashAndPass[0] + "', '" + hashAndPass[1] + "', '" + createUserStringPermissions + "', '1');";
                int doQueryAccountsTable = MainApp.myJDBC.executeUpdateQuery(queryAccounts);
                if (doQueryEmployeeTable == -1 || doQueryAccountsTable == -1) {
                    createUserInfo.setTextFill(Paint.valueOf("d81e05"));
                    createUserInfo.setFont(Font.font(10));
                    createUserInfo.setText(data.getResourceBundle().getString("Something went wrong in the SQL query.\n Please contact your local sysadmin."));
                }

                createUserAlert = new Alert(AlertType.INFORMATION);
                createUserAlert.initOwner(data.getStage());
                createUserAlert.setTitle("Account creation");
                createUserAlert.setHeaderText("Account succesfully created.");
                createUserAlert.setContentText("A new user has been created with the following information:"
                        + "Username: " + createUserStringName + "\n");
                createUserAlert.showAndWait();

                // Empty everything 
                createUserPassword.clear();
                createUserRealname.clear();
                createUserBetweenName.clear();
                createUserLastname.clear();
                createUserEmail.clear();

                roleAdmin.setSelected(false);
                roleManager.setSelected(false);
                roleEmployee.setSelected(true);

                setupTableView();

                // Confirm to the user that the account was succesfully created
                createUserInfo.setTextFill(Paint.valueOf("green"));
                createUserInfo.setFont(Font.font(12));
                createUserInfo.setText(data.getResourceBundle().getString("accountCreatedInfo"));

            } else {

            }

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

            String deactivateQuery = "UPDATE `account` SET `active`='0' WHERE `Employee_code`='" + userToDeactivate + "';";
            String activateQuery = "UPDATE `account` SET `active`='1' WHERE `Employee_code`='" + userToDeactivate + "';";
            String activeCheckQuery = "SELECT active FROM account WHERE Employee_code = '" + userToDeactivate + "'";

            if (MainApp.myJDBC.executeStringQuery(activeCheckQuery).equals("1")) {
                Alert deactivateUserAlert = new Alert(AlertType.CONFIRMATION);
                String imageURL = this.getClass().getResource("/images/32190-200.png").toString();
                Image image = new Image(imageURL, 64, 64, false, true);

                deactivateUserAlert.initOwner(data.getStage());
                deactivateUserAlert.setTitle("Deactivate user!");
                deactivateUserAlert.setContentText("Are you sure you want to deactivate user " + userToDeactivate);
                deactivateUserAlert.setGraphic(new ImageView(image));

                Optional<ButtonType> deactivateUserAnswer = deactivateUserAlert.showAndWait();
                if (deactivateUserAnswer.get() == ButtonType.OK) {
                    MainApp.myJDBC.executeUpdateQuery(deactivateQuery);
                }
            } else {
                Alert deactivateUserAlert = new Alert(AlertType.CONFIRMATION);
                String imageURL = this.getClass().getResource("/images/32190-200.png").toString();
                Image image = new Image(imageURL, 64, 64, false, true);

                deactivateUserAlert.initOwner(data.getStage());
                deactivateUserAlert.setTitle("Deactivate user!");
                deactivateUserAlert.setContentText("Are you sure you want to activate user " + userToDeactivate);
                deactivateUserAlert.setGraphic(new ImageView(image));

                Optional<ButtonType> deactivateUserAnswer = deactivateUserAlert.showAndWait();
                if (deactivateUserAnswer.get() == ButtonType.OK) {
                    MainApp.myJDBC.executeUpdateQuery(activateQuery);
                }
            }
            deactivateUserInfo.setTextFill(Paint.valueOf("green"));
            deactivateUserInfo.setText(data.getResourceBundle().getString("accountDeactivatedInfo"));
            setupTableView();
        }
    }

    private String getNextStaffID() {
        // Get next staff ID
        String query = "SELECT MAX(code)"
                + "FROM employee";
        String result = MainApp.myJDBC.executeStringQuery(query);
        result = Integer.toString(Integer.parseInt(result) + 1);
        return result;
    }

    private void setupTableView() {
        for (int cnr = 0; cnr < TableViewUsers.getColumns().size(); cnr++) {
            TableColumn tc = (TableColumn) TableViewUsers.getColumns().get(cnr);
            String propertyName = tc.getId();
            if (propertyName != null && !propertyName.isEmpty()) {
                tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                System.out.printf("Attached column %s in tableview to matching attribute.\n", propertyName);
            }
        }

        reloadTable();

        FilteredList<User> filteredData = new FilteredList<>(userList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(users -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (users.getFirstName() != null && users.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (users.getLastName() != null && users.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (users.getAirport() != null && users.getAirport().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (users.getPermissions() != null && users.getPermissions().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (users.getUsername() != null && users.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<User> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(TableViewUsers.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TableViewUsers.setItems(sortedData);

        // This uh... works okay?
        TableViewUsers.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User rowData = row.getItem();
                    resetUser.setText(rowData.getUsername());
                    deactivateUsername.setText(rowData.getUsername());
                }
            });
            return row;
        });
    }

    public void reloadTable() {
        try {
            userList.clear();
            String query = "SELECT Employee_code,first_name,preposition,last_name,user_level,Luchthaven_IATA,active FROM account JOIN employee ON Employee_code = code;";
            ResultSet result = MainApp.myJDBC.executeResultSetQuery(query);

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
                    RealActive = data.getResourceBundle().getString("inactive");
                } else {
                    RealActive = data.getResourceBundle().getString("active");
                }
                switch (TBpermissions) {
                    case 0:
                        realPermissions = data.getResourceBundle().getString("deskEmployee");
                        break;
                    case 1:
                        realPermissions = data.getResourceBundle().getString("manager");
                        break;
                    case 2:
                        realPermissions = data.getResourceBundle().getString("admin");
                        break;
                    default:
                        realPermissions = "Invalid";
                        break;
                }

                if (!result.getString("preposition").isEmpty() && result.getString("preposition") != null) {
                    TBpreposition = result.getString("preposition");
                }
                User storingUsers = new User(TBusername, TBfirstName, TBlastName, TBIATA, RealActive, realPermissions);

                if (TBpreposition != null && !TBpreposition.isEmpty()) {
                    storingUsers.setPreposition(TBpreposition);
                }
                userList.add(storingUsers);
            }

            TableViewUsers.setItems(userList);

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

        setupTableView();

        //
        airportBox.setItems(Airports);
    }
}
