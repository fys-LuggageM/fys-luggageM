package fys.luggagem;

import fys.luggagem.models.Customer;
import fys.luggagem.models.Data;
import fys.luggagem.models.Email;
import fys.luggagem.models.Luggage;
import fys.luggagem.models.Print;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Jordan van Beijnhem <jordan.van.beijnhem@hva.nl>
 */
public class VerlorenBagageController implements Initializable {

    private Data data = MainApp.getData();
    private MyJDBC myJDBC = MainApp.myJDBC;
    private Customer customer = MainApp.getCustomer();
    private String IATA = "AMS";
    private ObservableList<String> Airports = FXCollections.observableArrayList();
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
    public void handleSaveAction(ActionEvent event) throws SQLException, InterruptedException {
        Luggage luggage = createNewLostLuggage();
        luggage.foundLuggageToDatabase(customer);
        setEmailContent(luggage);
        createChoiceAlert();
        customer.clear();
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
        BooleanBinding bb = luggageTypeField.textProperty().isEmpty().or(brandField.textProperty().isEmpty()).or(
                flightField.textProperty().isEmpty()).or(destinationField.textProperty().isEmpty()).or(
                primaryColorField.textProperty().isEmpty()).or(airportBox.valueProperty().isNull());

        saveButton.disableProperty().bind(bb);

        setupAiportBox();
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
        airportBox.setDisable(toggle);
        luggageTypeField.setDisable(toggle);
        brandField.setDisable(toggle);
        flightField.setDisable(toggle);
        tagField.setDisable(toggle);
        primaryColorField.setDisable(toggle);
        secondaryColorField.setDisable(toggle);
        notesField.setDisable(toggle);
        destinationField.setDisable(toggle);
    }

    private void fillInFields() {
        initialsField.setText(customer.getFirstName().charAt(0) + ".");
        prepositionField.setText(customer.getPreposition() != null ? customer.getPreposition() : "");
        lastNameField.setText(customer.getLastName());
        cityField.setText(customer.getCity());
    }

    private Luggage createNewLostLuggage() throws SQLException {
        myJDBC.newRegnrLostLuggage();
        Luggage lostLuggage = new Luggage();
        lostLuggage.setRegistrationNr(myJDBC.getLuggageRegistrationNr());
        lostLuggage.setIATA((String) airportBox.getSelectionModel().getSelectedItem()); //test value
        lostLuggage.setLuggageType(!luggageTypeField.getText().isEmpty() ? luggageTypeField.getText() : null);
        lostLuggage.setBrand(!brandField.getText().isEmpty() ? brandField.getText() : null);
        lostLuggage.setDestination(!destinationField.getText().isEmpty() ? destinationField.getText() : null);
        lostLuggage.setFlightNr(!flightField.getText().isEmpty() ? flightField.getText() : null);
        lostLuggage.setLabelNr(!tagField.getText().isEmpty() ? tagField.getText() : null);
        lostLuggage.setPrimaryColor(!primaryColorField.getText().isEmpty() ? primaryColorField.getText() : null);
        lostLuggage.setSecondaryColor(!secondaryColorField.getText().isEmpty() ? secondaryColorField.getText() : null);
        lostLuggage.setTravellerName(customer.getFirstName() + (customer.getPreposition() != null
                ? " " + customer.getPreposition() : "") + " " + customer.getLastName());
        lostLuggage.setNotes(!notesField.getText().isEmpty() ? notesField.getText() : null);

        return lostLuggage;
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
                + data.getName();
    }

    private void setupAiportBox() {
        try {
            // Do an SQL query to get all airports
            String query = "SELECT IATA FROM airport;";
            ResultSet result = MainApp.myJDBC.executeResultSetQuery(query);
            // Loop over de resultset
            while (result.next()) {
                // Voeg de individuele results to aan de database
                Airports.add(result.getString("IATA"));
            }
            // Set the items of the airportbox to the observable list
            airportBox.setItems(Airports);
        } catch (SQLException ex) {
            Logger.getLogger(VerlorenBagageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createChoiceAlert() {

        String imageURL = this.getClass().getResource("/images/share-option.png").toString();
        Image image = new Image("/images/share-option.png", 64, 64, false, true);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView(image));
        alert.initOwner(data.getStage());
        alert.setTitle("");
        alert.setHeaderText(data.getResourceBundle().getString("confirmation"));
        alert.setContentText(data.getResourceBundle().getString("shareAsk"));

        ButtonType buttonTypeOne = new ButtonType(data.getResourceBundle().getString("emailing"));
        ButtonType buttonTypeTwo = new ButtonType(data.getResourceBundle().getString("printing"));
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            Email.sendEmail(customer.getEmailAdres(), "Lost Luggage", email);
            goToMatching();
        } else if (result.get() == buttonTypeTwo) {
            data.getStage().setIconified(true);
            Print.printPdf();
            data.getStage().setIconified(false);
            goToMatching();
        } else {

        }
    }
}
