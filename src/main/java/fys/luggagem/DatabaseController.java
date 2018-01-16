package fys.luggagem;

import static fys.luggagem.MainApp.data;
import fys.luggagem.models.Data;
import fys.luggagem.models.Luggage;
import fys.luggagem.models.Matching;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DatabaseController implements Initializable {

    private Data data = MainApp.getData();
    private MyJDBC db = MainApp.myJDBC;

    @FXML
    private Label label;
    @FXML
    private Label nameLabel;
    @FXML
    private Button verlorenBagageButton;
    @FXML
    private Button beschadigdeBagageButton;
    @FXML
    private Button gevondenBagageBeschadigde;
    @FXML
    private TableView<Luggage> TableViewLuggage;
    @FXML
    private TableColumn<?, ?> brand;

    private ObservableList<Luggage> foundLuggageList = FXCollections.observableArrayList();

    private ObservableList<Luggage> lostLuggageList = FXCollections.observableArrayList();

    private ObservableList<Luggage> damagedLuggageList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> registrationNr;
    @FXML
    private TableColumn<?, ?> flightNr;
    @FXML
    private TableColumn<?, ?> labelNr;
    @FXML
    private TableColumn<?, ?> luggageType;
    @FXML
    private TableColumn<?, ?> IATA;

    private String currentlyActiveTable;
    @FXML
    private TextField searchLuggage;

    @FXML
    private void handleBeschadigdeButtonAction(ActionEvent event) {
        setupDamagedLuggageTable();
        label.setText(data.getResourceBundle().getString("damagedLuggageData"));
    }

    @FXML
    private void handleVerlorenButtonAction(ActionEvent event) {
        setupLostLuggageTable();
        label.setText(data.getResourceBundle().getString("lostLuggageData"));
    }

    @FXML
    private void handleGevondenButtonAction(ActionEvent event) {
        setupFoundLuggageTable();
        label.setText(data.getResourceBundle().getString("foundLuggageData"));
    }

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

    private void setupDamagedLuggageTable() {
        try {
            damagedLuggageList.clear();
            for (int cnr = 0; cnr < TableViewLuggage.getColumns().size(); cnr++) {
                TableColumn tc = (TableColumn) TableViewLuggage.getColumns().get(cnr);
                String propertyName = tc.getId();
                if (propertyName != null && !propertyName.isEmpty()) {
                    tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                    System.out.printf("Attached column %s in tableview to matching attribute.\n", propertyName);
                }
            }

            String query = "SELECT registrationnr, labelnr, airport_IATA, brand, flightnr, luggage_type FROM luggage WHERE case_type = 3 AND case_status = 1;";

            ResultSet foundLuggage = MainApp.myJDBC.executeResultSetQuery(query);

            while (foundLuggage.next()) {
                int registrationnr = foundLuggage.getInt("registrationnr");
                String flightnr = foundLuggage.getString("flightnr");
                String labelnr = foundLuggage.getString("labelnr");
                String luggagetype = foundLuggage.getString("luggage_type");
                String brandy = foundLuggage.getString("brand");
                String rIATA = foundLuggage.getString("airport_IATA");

                Luggage luggageObject = new Luggage(registrationnr, flightnr, labelnr, luggagetype, brandy, rIATA);
                System.out.println("Added Luggage object to list.");

                damagedLuggageList.add(luggageObject);
            }

            setupSearch(damagedLuggageList);
            currentlyActiveTable = "damagedLuggage";

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupLostLuggageTable() {
        try {
            lostLuggageList.clear();
            for (int cnr = 0; cnr < TableViewLuggage.getColumns().size(); cnr++) {
                TableColumn tc = (TableColumn) TableViewLuggage.getColumns().get(cnr);
                String propertyName = tc.getId();
                if (propertyName != null && !propertyName.isEmpty()) {
                    tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                    System.out.printf("Attached column %s in tableview to matching attribute.\n", propertyName);
                }
            }

            String query = "SELECT registrationnr, labelnr, airport_IATA, brand, flightnr, luggage_type FROM luggage WHERE case_type = 2 AND case_status = 1;";

            ResultSet foundLuggage = MainApp.myJDBC.executeResultSetQuery(query);

            while (foundLuggage.next()) {
                int registrationnr = foundLuggage.getInt("registrationnr");
                String flightnr = foundLuggage.getString("flightnr");
                String labelnr = foundLuggage.getString("labelnr");
                String luggagetype = foundLuggage.getString("luggage_type");
                String brandy = foundLuggage.getString("brand");
                String rIATA = foundLuggage.getString("airport_IATA");

                Luggage luggageObject = new Luggage(registrationnr, flightnr, labelnr, luggagetype, brandy, rIATA);
                System.out.println("Added Luggage object to list.");

                lostLuggageList.add(luggageObject);
            }

            setupSearch(lostLuggageList);
            currentlyActiveTable = "lostLuggage";

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setupFoundLuggageTable() {
        try {
            foundLuggageList.clear();
            for (int cnr = 0; cnr < TableViewLuggage.getColumns().size(); cnr++) {
                TableColumn tc = (TableColumn) TableViewLuggage.getColumns().get(cnr);
                String propertyName = tc.getId();
                if (propertyName != null && !propertyName.isEmpty()) {
                    tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                    System.out.printf("Attached column %s in tableview to matching attribute.\n", propertyName);
                }
            }

            String query = "SELECT registrationnr, labelnr, airport_IATA, brand, flightnr, luggage_type FROM luggage WHERE case_type = 1 AND case_status = 1;";

            ResultSet foundLuggage = MainApp.myJDBC.executeResultSetQuery(query);

            while (foundLuggage.next()) {
                int registrationnr = foundLuggage.getInt("registrationnr");
                String flightnr = foundLuggage.getString("flightnr");
                String labelnr = foundLuggage.getString("labelnr");
                String luggagetype = foundLuggage.getString("luggage_type");
                String brandy = foundLuggage.getString("brand");
                String rIATA = foundLuggage.getString("airport_IATA");

                Luggage luggageObject = new Luggage(registrationnr, flightnr, labelnr, luggagetype, brandy, rIATA);
                System.out.println("Added Luggage object to list.");

                foundLuggageList.add(luggageObject);
            }
            setupSearch(foundLuggageList);
            currentlyActiveTable = "foundLuggage";

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadpopupFXML() {
        TableViewLuggage.setRowFactory(tv -> {
            TableRow<Luggage> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Luggage rowData = row.getItem();
                        MainApp.data.setLuggageRegistrationNumber(rowData.getRegistrationNr());
                        Parent parent;
                        if ("damagedLuggage".equals(currentlyActiveTable)) {
                            parent = FXMLLoader.load(getClass().getResource("/fxml/BeschadigdeBagageEdit.fxml"), data.getResourceBundle());
                        } else {
                            parent = FXMLLoader.load(getClass().getResource("/fxml/DatabaseEdit.fxml"), data.getResourceBundle());
                        }
                        Stage stage = new Stage(StageStyle.DECORATED);
                        switch (currentlyActiveTable) {
                            case "lostLuggage":
                                stage.setTitle(MainApp.data.getResourceBundle().getString("editing") + " "
                                        + MainApp.data.getResourceBundle().getString("luggageLost"));
                                break;
                            case "foundLuggage":
                                stage.setTitle(MainApp.data.getResourceBundle().getString("editing") + " "
                                        + MainApp.data.getResourceBundle().getString("luggageFound"));
                                break;
                            default:
                                stage.setTitle(MainApp.data.getResourceBundle().getString("editing") + " "
                                        + MainApp.data.getResourceBundle().getString("luggageDamaged"));
                                break;
                        }
                        stage.setScene(new Scene(parent));
                        stage.setAlwaysOnTop(true);
                        stage.initOwner(data.getStage());
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });
    }

    public void setupSearch(ObservableList<Luggage> list) {
        FilteredList<Luggage> filteredData = new FilteredList<>(list, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchLuggage.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(luggage -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (Integer.toString(luggage.getRegistrationNr()) != null && Integer.toString(luggage.getRegistrationNr()).contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (luggage.getDestination() != null && luggage.getDestination().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (luggage.getBrand() != null && luggage.getBrand().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (luggage.getLuggageType() != null && luggage.getLuggageType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (luggage.getFlightNr() != null && luggage.getFlightNr().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (luggage.getLabelNr() != null && luggage.getLabelNr().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Luggage> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(TableViewLuggage.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TableViewLuggage.setItems(sortedData);
    }

    @FXML
    private void matchingEvent(ActionEvent event) throws IOException {
        handleCloseAction(event);
        Luggage luggage = getSelectedMatch();
        db.setLuggageRegistrationNr(luggage.getRegistrationNr());
        goToMatching();
    }

    private Luggage getSelectedMatch() {
        return (Luggage) TableViewLuggage.getSelectionModel().getSelectedItem();
    }

    private void goToMatching() {
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/MatchingFXML.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleBeschadigdeButtonAction(null);
        loadpopupFXML();
    }
}
