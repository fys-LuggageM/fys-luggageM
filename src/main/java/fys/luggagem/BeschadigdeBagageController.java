package fys.luggagem;

import fys.luggagem.models.Customer;
import fys.luggagem.models.Data;
import fys.luggagem.models.Email;
import fys.luggagem.models.Print;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class BeschadigdeBagageController implements Initializable {

    private final Data data = MainApp.getData();
    private final MyJDBC db = MainApp.myJDBC;
    private final Customer customer = MainApp.getCustomer();
    private final ObservableList<String> Airports = FXCollections.observableArrayList();

    //To store the image URL's as strings
    private String placeholderURL;
    private String imageURL;
    private URI imageURL01;
    private URI imageURL02;
    private URI imageURL03;

    String email;

    @FXML
    private TextArea notes;

    @FXML
    private ComboBox airports;

    @FXML
    private ImageView image01;

    @FXML
    private ImageView image02;

    @FXML
    private ImageView image03;

    @FXML
    private Button saveImages;

    @FXML
    private Label savedConfirmation;

    @FXML
    private Button selectImage1;

    @FXML
    private Button selectImage2;

    @FXML
    private Button selectImage3;

    @FXML
    private Label customerSelected;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // setup combobox with airports
        getAirports();

        // disable save button if text fields are empty
        BooleanBinding bb = notes.textProperty().isEmpty().or(airports.valueProperty().isNull());
        saveImages.disableProperty().bind(bb);

        //check if customer object is selected
        if (customer.checkIfEmpty()) {
            toggleButtons(true);
            customerSelected.setVisible(false);
        } else {
            toggleButtons(false);
            customerSelected.setVisible(true);
        }
    }

    private void toggleButtons(boolean toggle) {
        notes.setDisable(toggle);
        airports.setDisable(toggle);
        selectImage1.setDisable(toggle);
        selectImage2.setDisable(toggle);
        selectImage3.setDisable(toggle);

    }

    @FXML
    public void handleNewCustomerAction(ActionEvent event) throws IOException {
        data.setLastScene("/fxml/BeschadigdeBagageFXML.fxml");
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/NewCustomerFXML.fxml"));
    }

    @FXML
    public void handleExistingCustomerAction(ActionEvent event) {

        data.setLastScene("/fxml/BeschadigdeBagageFXML.fxml");
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/ExistingCustomerFXML.fxml"));
    }

    @FXML
    public void goMatching(ActionEvent event) throws IOException {
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/MatchingFXML.fxml"));
    }

    @FXML
    private void loadImage01(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteer de foto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(data.getStage());

        if (file != null) {
            imageURL = file.toURI().toURL().toString();
            imageURL01 = file.toURI();

            Image image = new Image(imageURL, 308, 205, false, false);
            image01.setImage(image);
        }
    }

    @FXML
    private void loadImage02(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteer de foto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(data.getStage());

        if (file != null) {
            imageURL = file.toURI().toURL().toString();
            imageURL02 = file.toURI();
            Image image = new Image(imageURL, 308, 205, false, false);
            image02.setImage(image);
        }
    }

    @FXML
    private void loadImage03(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteer de foto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(data.getStage());

        if (file != null) {
            imageURL = file.toURI().toURL().toString();
//            System.out.print(imageURL);
            imageURL03 = file.toURI();

            Image image = new Image(imageURL, 308, 205, false, false);
            image03.setImage(image);
        }
    }

    // Method to insert the selected images
    private void uploadImageQuery(ActionEvent event) throws IOException, SQLException {
        Connection conn = db.getConnection();

        // insert query for images
        String INSERT_PICTURE = "INSERT luggage_damaged (image01, image02, image03, Luggage_registrationnr) values (?, ?, ?, ?)";

        FileInputStream fis01 = null;
        FileInputStream fis02 = null;
        FileInputStream fis03 = null;

        File file01 = null;
        File file02 = null;
        File file03 = null;
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            //image01
            if (imageURL01 != null) {
                file01 = new File(imageURL01);
                file01.toURI();
                fis01 = new FileInputStream(file01);
            }
            //image02
            if (imageURL02 != null) {
                file02 = new File(imageURL02);
                file02.toURI();
                fis02 = new FileInputStream(file02);
            }
            //image03
            if (imageURL03 != null) {
                file03 = new File(imageURL03);
                file03.toURI();
                fis03 = new FileInputStream(file03);
            }

            //preparedstatement
            ps = conn.prepareStatement(INSERT_PICTURE);
            if (file01 != null) {
                ps.setBinaryStream(1, fis01, (int) file01.length());
            } else {
                ps.setBinaryStream(1, fis01);
            }
            if (file02 != null) {
                ps.setBinaryStream(2, fis02, (int) file02.length());
            } else {
                ps.setBinaryStream(2, fis02);
            }
            if (file03 != null) {
                ps.setBinaryStream(3, fis03, (int) file03.length());
            } else {
                ps.setBinaryStream(3, fis03);
            }
            ps.setInt(4, db.getRegNrDamaged());
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            System.err.println("\n uploadImageQuery: " + e);
        }
    }

    private void setFields() {
        String setNotes = notes.getText();
        String setAirport = airports.getValue().toString();
        System.out.println("GET NOTES: " + setNotes);

        Connection conn = db.getConnection();

        String setInfo2 = "UPDATE luggage SET airport_IATA = ?, notes = ?, customer_firstname = ?, customer_preposition = ?, customer_lastname = ?, customer_customernr = ? WHERE registrationnr = ?";

        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(setInfo2);

            ps.setString(1, setAirport);
            ps.setString(2, setNotes);
            ps.setString(3, customer.getFirstName());
            ps.setString(4, customer.getPreposition());
            ps.setString(5, customer.getLastName());
            ps.setInt(6, customer.getCustomerNr());
            ps.setInt(7, db.getRegNrDamaged());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.err.print("\nsetfields: " + e);
        }
    }

    private void getAirports() {
        try {
            String query = "SELECT IATA from Airport";

            ResultSet result = db.executeResultSetQuery(query);
            while (result.next()) {
                Airports.add(result.getString("IATA"));
            }
            airports.setItems(Airports);
        } catch (SQLException ex) {
            Logger.getLogger(BeschadigdeBagageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changesLogToDatabase() {
        String changesLogToDatabase2 = "INSERT INTO changes (changeid, Employee_code, Luggage_registrationnr) "
                + "VALUES (0, " + data.getEmployeeNr() + ", " + db.getRegNrDamaged() + ");";
        db.executeUpdateQuery(changesLogToDatabase2);
    }

    @FXML
    public void saveToDatabase(ActionEvent event) {
        // Image inside alert dialog resized to 65x50
        imageURL = this.getClass().getResource("/images/upload_button02.png").toString();
        Image image = new Image(imageURL, 64, 64, false, true);

        //Create new alert dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        // Alert dialog setup
        alert.initOwner(data.getStage());
        alert.setGraphic(new ImageView(image));
        alert.setTitle(data.getResourceBundle().getString("dmgAlertConfirmationTitle"));
        alert.setHeaderText(data.getResourceBundle().getString("dmgAlertConfirmationHeader"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //set saved succesfull label visible for 5 seconds and disable save button
            saveImages.setVisible(false);
            savedConfirmation.setVisible(true);

            try {
                // create new registrationnr
                db.newRegnrDamagedLuggage();

            } catch (SQLException ex) {
                System.out.println("\nnewRegnrDamagedLuggage (MyJDBC): " + ex);
            }
            setFields();
            changesLogToDatabase();
            try {
                // upload selected images
                uploadImageQuery(event);
            } catch (IOException | SQLException ex) {
                System.out.println("uploadImageQuery: " + ex);
            }

            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    savedConfirmation.setVisible(false);
                    saveImages.setVisible(true);

                }
            });
            pause.play();

            // ask to send email or print confirmation
            setEmailContent();
            createChoiceAlert();

            // clear all input field and set placeholder for images
            placeholderURL = this.getClass().getResource("/images/placeholder-600x400.png").toString();
            Image placeholder = new Image(placeholderURL);
            image01.setImage(placeholder);
            image02.setImage(placeholder);
            image03.setImage(placeholder);
            notes.clear();
            customer.clear();
            MainApp.loadFXMLFile(this.getClass().getResource("/fxml/BeschadigdeBagageFXML.fxml"));
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

    private void setEmailContent() {
        email = "Dear Customer, \n\n"
                + "You have brought it to our attention that your luggage has been damaged. \n"
                + "We have noted the following details: \n\n"
                + "Your name: " + customer.getFirstName()
                + (customer.getPreposition() != null && !customer.getPreposition().isEmpty()
                ? " " + customer.getPreposition() + " " : " ")
                + customer.getLastName() + "\n\n"
                + notes.getText() + "\n"
                + "\n\nIf this information is not correct, contact us as soon as possible.\n\n"
                + "We're sorry for the inconvience!\n\n"
                + "Kind regards,\n\n"
                + data.getName();
    }

    private void createChoiceAlert() {

//        String imageURL = this.getClass().getResource("/images/share-option.png").toString();
        Image image = new Image("/images/share-option.png", 64, 64, false, true);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView(image));
        alert.initOwner(data.getStage());
        alert.setTitle("");
        alert.setHeaderText(data.getResourceBundle().getString("confirmation"));
        alert.setContentText(data.getResourceBundle().getString("shareAsk"));

        ButtonType buttonTypeOne = new ButtonType(data.getResourceBundle().getString("emailing"));
        ButtonType buttonTypeTwo = new ButtonType(data.getResourceBundle().getString("printing"));
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            Email.sendEmail(customer.getEmailAdres(), "Lost Luggage", email);
            customerSelected.setVisible(false);

        } else if (result.get() == buttonTypeTwo) {
            data.getStage().setIconified(true);
            Print.printDamagedLuggagePdf(notes.getText());
            data.getStage().setIconified(false);
            customerSelected.setVisible(false);
        } else {

        }
    }
}
