package fys.luggagem;

import fys.luggagem.models.Customer;
import fys.luggagem.models.Data;
import fys.luggagem.models.Email;
import fys.luggagem.models.Luggage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private String email;

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
    public void handleSaveAction(ActionEvent event) throws SQLException {
        Luggage luggage = createNewFoundLuggage();
        luggage.foundLuggageToDatabase(customer);
        setEmailContent(luggage);
        Email.sendEmail("test@test.com", "Lost Luggage", email);
        customer.clear();
        goToMatching();

    }

    private void goToMatching() {
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/MatchingFXML.fxml"));
    }

    @FXML
    public void handleNewCustomerAction(ActionEvent event) throws IOException {
        data.setLastScene("/fxml/VerlorenBagageFXML.fxml");
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/NewCustomerFXML.fxml"));
    }

    @FXML
    public void handleExistingCustomerAction(ActionEvent event) {
        data.setLastScene("/fxml/VerlorenBagageFXML.fxml");
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

    private Luggage createNewFoundLuggage() throws SQLException {
        myJDBC.newRegnrFoundLuggage();
        Luggage foundLuggage = new Luggage();
        foundLuggage.setRegistrationNr(myJDBC.getLuggageRegistrationNr());
        foundLuggage.setIATA(IATA); //test value
        foundLuggage.setLuggageType(!luggageTypeField.getText().isEmpty() ? luggageTypeField.getText() : null);
        foundLuggage.setBrand(!brandField.getText().isEmpty() ? brandField.getText() : null);
        foundLuggage.setDestination(!destinationField.getText().isEmpty() ? destinationField.getText() : null);
        foundLuggage.setFlightNr(!flightField.getText().isEmpty() ? flightField.getText() : null);
        foundLuggage.setLabelNr(!tagField.getText().isEmpty() ? tagField.getText() : null);
        foundLuggage.setPrimaryColor(!primaryColorField.getText().isEmpty() ? primaryColorField.getText() : null);
        foundLuggage.setSecondaryColor(!secondaryColorField.getText().isEmpty() ? secondaryColorField.getText() : null);
        foundLuggage.setTravellerName(customer.getFirstName() + (customer.getPreposition() != null
                ? " " + customer.getPreposition() : "") + " " + customer.getLastName());
        foundLuggage.setNotes(!notesField.getText().isEmpty() ? notesField.getText() : null);

        return foundLuggage;
    }

    private void setEmailContent(Luggage luggage) {
        email = "Dear Customer, \n\n"
                + "You have brought it to our attention that you have lost your luggage. \n"
                + "We have noted the following details: \n\n"
                + "\tRegistration number: " + luggage.getRegistrationNr() + "\n"
                + "\tAirport: " + luggage.getIATA() + "\n"
                + "\tLuggage Type: " + luggage.getLuggageType() + "\n"
                + "\tBrand: " + luggage.getBrand() + "\n"
                + "\tDestination: " + luggage.getDestination() + "\n"
                + "\tFlight number: " + luggage.getFlightNr() + "\n"
                + "\tLabel number: " + luggage.getLabelNr() + "\n"
                + "\tColor: " + luggage.getPrimaryColor()
                + (luggage.getSecondaryColor() != null ? " and " + luggage.getSecondaryColor() : "") + "\n"
                + "\tYour name: " + customer.getFirstName()
                + (customer.getPreposition() != null && !customer.getPreposition().isEmpty()
                ? " " + customer.getPreposition() + " " : " ")
                + customer.getLastName() + "\n"
                + "\tExtra notes: " + luggage.getNotes()
                + "\n\nIf this information is not correct, contact us as soon as possible.\n\n"
                + "We're sorry for the inconvience!\n\n"
                + "Kind regards,\n\n"
                + "Pathe Dude";
    }
}
