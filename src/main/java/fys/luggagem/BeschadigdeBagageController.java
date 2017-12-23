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
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.binding.BooleanBinding;
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
import javafx.stage.Stage;
import javafx.util.Duration;

public class BeschadigdeBagageController implements Initializable {

    private Data data = MainApp.getData();
    private MyJDBC db = MainApp.myJDBC;
    private Customer customer = MainApp.getCustomer();

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
    private ComboBox comboBox;

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
        Stage stage = new Stage();
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
        Stage stage = new Stage();
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
        Stage stage = new Stage();
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
        String setAirport = comboBox.getValue().toString();
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

    @FXML
    public void saveToDatabase(ActionEvent event) {
        // Image inside alert dialog resized to 65x50
        imageURL = this.getClass().getResource("/images/upload_button02.png").toString();
        Image image = new Image(imageURL, 64, 64, false, true);

        System.out.println("CUSTOMER NR: " + customer.getCustomerNr());

        //Create new alert dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);

        // Alert dialog setup
        alert.initOwner(data.getStage());
        alert.setGraphic(new ImageView(image));
        alert.setTitle("Damaged luggage");
        alert.setHeaderText("Save and upload to database");
        alert.setContentText("Are you sure?");

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

            // start the pause timer    
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // add items to combobox and set pre selection to"AMS"
        comboBox.getItems().addAll("AMS");
        comboBox.getSelectionModel().select("AMS");

        // booleanbinding to check if textfields are empty
        // if so, disable the save button to prevent accidental uploads to the database
        BooleanBinding bb = notes.textProperty().isEmpty();

        saveImages.disableProperty().bind(bb);

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
        alert.setHeaderText("Bevestiging");
        alert.setContentText("Hoe wilt u de bevestiging delen met de klant?");

        ButtonType buttonTypeOne = new ButtonType("Email");
        ButtonType buttonTypeTwo = new ButtonType("Printen");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            Email.sendEmail(customer.getEmailAdres(), "Lost Luggage", email);
        } else if (result.get() == buttonTypeTwo) {
            data.getStage().setIconified(true);
            Print.printPdf();
            data.getStage().setIconified(false);
        } else {

        }
    }
}
