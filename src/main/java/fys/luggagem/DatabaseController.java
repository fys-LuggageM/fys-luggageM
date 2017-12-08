package fys.luggagem;

import fys.luggagem.models.Data;
import fys.luggagem.models.Luggage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DatabaseController implements Initializable {

    private Data data = MainApp.getData();

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

    @FXML
    private void handleBeschadigdeButtonAction(ActionEvent event) {
        System.out.println("Beschadigde bagage");
        setupDamagedLuggageTable();
        label.setText("data van Beschadigde Bagage");
    }

    @FXML
    private void handleVerlorenButtonAction(ActionEvent event) {
        System.out.println("Verloren bagage");
        setupLostLuggageTable();
        label.setText("data van Verloren Bagage");
    }

    @FXML
    private void handleGevondenButtonAction(ActionEvent event) {
        System.out.println("Gevonden bagage");
        setupFoundLuggageTable();
        label.setText("data van Gevonden Bagage");
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
                String IATA = foundLuggage.getString("airport_IATA");

                Luggage luggageObject = new Luggage(registrationnr, flightnr, labelnr, luggagetype, brandy, IATA);
                System.out.println("Added Luggage object to list.");

                damagedLuggageList.add(luggageObject);
            }

            TableViewLuggage.setItems(damagedLuggageList);

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

            String query = "SELECT registrationnr, labelnr, airport_IATA, brand, flightnr, luggage_type FROM luggage WHERE case_type = 3 AND case_status = 1;";

            ResultSet foundLuggage = MainApp.myJDBC.executeResultSetQuery(query);

            while (foundLuggage.next()) {
                int registrationnr = foundLuggage.getInt("registrationnr");
                String flightnr = foundLuggage.getString("flightnr");
                String labelnr = foundLuggage.getString("labelnr");
                String luggagetype = foundLuggage.getString("luggage_type");
                String brandy = foundLuggage.getString("brand");
                String IATA = foundLuggage.getString("airport_IATA");

                Luggage luggageObject = new Luggage(registrationnr, flightnr, labelnr, luggagetype, brandy, IATA);
                System.out.println("Added Luggage object to list.");

                lostLuggageList.add(luggageObject);
            }

            TableViewLuggage.setItems(lostLuggageList);

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

            String query = "SELECT registrationnr, labelnr, airport_IATA, brand, flightnr, luggage_type FROM luggage WHERE case_type = 2 AND case_status = 1;";

            ResultSet foundLuggage = MainApp.myJDBC.executeResultSetQuery(query);

            while (foundLuggage.next()) {
                int registrationnr = foundLuggage.getInt("registrationnr");
                String flightnr = foundLuggage.getString("flightnr");
                String labelnr = foundLuggage.getString("labelnr");
                String luggagetype = foundLuggage.getString("luggage_type");
                String brandy = foundLuggage.getString("brand");
                String IATA = foundLuggage.getString("airport_IATA");

                Luggage luggageObject = new Luggage(registrationnr, flightnr, labelnr, luggagetype, brandy, IATA);
                System.out.println("Added Luggage object to list.");

                foundLuggageList.add(luggageObject);
            }

            TableViewLuggage.setItems(foundLuggageList);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupFoundLuggageTable();
    }
}
