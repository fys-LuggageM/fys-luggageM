package fys.luggagem;

import fys.luggagem.models.Customer;
import fys.luggagem.models.Data;
import fys.luggagem.models.Luggage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author Jordan van Beijnhem <jordan.van.beijnhem@hva.nl>
 */
public class VerlorenBagageController implements Initializable {

    private Data data = MainApp.getData();
    private MyJDBC myJDBC = MainApp.myJDBC;
    private Customer customer = MainApp.getCustomer();
    private String IATA = "AMS";

    @FXML
    private ComboBox airportBox;

    @FXML
    private TextField destinationField;

    @FXML
    private Label customerAddedLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button newCustomerButton;

    @FXML
    private Button existingCustomerButton;

    @FXML
    private TextField airportField;

    @FXML
    private TextField luggageTypeField;

    @FXML
    private TextField brandField;

    @FXML
    private TextField flightField;

    @FXML
    private TextField tagField;

    @FXML
    private TextField primaryColorField;

    @FXML
    private TextField secondaryColorField;

    @FXML
    private TextField initialsField;

    @FXML
    private TextField prepositionField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField cityField;

    @FXML
    private TextArea notesField;

    @FXML
    public void handleSaveAction(ActionEvent event) {
        //TODO build a check method for required fields
        String sql = "INSERT INTO customer VALUES (546, '" + customer.getFirstName() + "', '"
                + (customer.getPreposition() != null ? customer.getPreposition() : "NULL") + "', '" + customer.getLastName()
                + "', '" + customer.getAdres() + "', '" + customer.getCity() + "', '" + customer.getPostalCode()
                + "', '" + customer.getCountry() + "', '" + customer.getPhoneNumber() + "', '"
                + customer.getEmailAdres() + "');";
        myJDBC.executeUpdateQuery(sql);
        customer.clear();
    }

    @FXML
    public void handleNewCustomerAction(ActionEvent event) throws IOException {
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/NewCustomerFXML.fxml"));
    }

    @FXML
    public void handleExistingCustomerAction(ActionEvent event) {
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/ExistingCustomerFXML.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (customer.checkIfEmpty()) {
            newCustomerButton.setStyle("-fx-background-color: #428bca;"
                    + "-fx-text-fill: white");
            existingCustomerButton.setStyle("-fx-background-color: #428bca;"
                    + "-fx-text-fill: white");
            toggleFields(true);
        } else {
            customerAddedLabel.setText("Customer selected!");
            newCustomerButton.setStyle("-fx-background-color: green;"
                    + "-fx-text-fill: white");
            newCustomerButton.setDisable(true);
            existingCustomerButton.setStyle("-fx-background-color: green;"
                    + "-fx-text-fill: white");
            existingCustomerButton.setDisable(true);
            toggleFields(false);
            fillInFields();
        }
    }

    private void toggleFields(boolean toggle) {
        luggageTypeField.setDisable(toggle);
        brandField.setDisable(toggle);
        flightField.setDisable(toggle);
        tagField.setDisable(toggle);
        primaryColorField.setDisable(toggle);
        secondaryColorField.setDisable(toggle);
        notesField.setDisable(toggle);
        saveButton.setDisable(toggle);
    }

    private void fillInFields() {
        initialsField.setText(customer.getFirstName().charAt(0) + ".");
        prepositionField.setText(customer.getPreposition() != null ? customer.getPreposition() : "");
        lastNameField.setText(customer.getLastName());
        cityField.setText(customer.getCity());
    }

    private void createNewFoundLuggage() {
        Luggage foundLuggage = new Luggage();
        foundLuggage.setIATA(IATA); //test value
        foundLuggage.setLuggageType(!luggageTypeField.getText().isEmpty() ? luggageTypeField.getText() : null);
        foundLuggage.setBrand(!brandField.getText().isEmpty() ? brandField.getText() : null);
        foundLuggage.setDestination(!destinationField.getText().isEmpty() ? flightField.getText() : null);
        foundLuggage.setFlightNr(!flightField.getText().isEmpty() ? flightField.getText() : null);
        foundLuggage.setLabelNr(!tagField.getText().isEmpty() ? tagField.getText() : null);
        foundLuggage.setPrimaryColor(!primaryColorField.getText().isEmpty() ? primaryColorField.getText() : null);
        foundLuggage.setSecondaryColor(!secondaryColorField.getText().isEmpty() ? secondaryColorField.getText() : null);
        foundLuggage.setDestination(customer.getFirstName() + (customer.getPreposition() != null
                ? " " + customer.getPreposition() : "") + " " + customer.getLastName());
        foundLuggage.setNotes(!notesField.getText().isEmpty() ? notesField.getText() : null);
    }
}
