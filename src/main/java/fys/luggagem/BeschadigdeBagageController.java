package fys.luggagem;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BeschadigdeBagageController implements Initializable {

    private Data data = MainApp.getData();

    //To store the image URL's as strings
    private String placeholderURL;
    private String imageURL;
    private URI imageURL01;
    private URI imageURL02;
    private URI imageURL03;

    //Will store the new/highest registrationNr
    private int registrationNr;

    private MyJDBC db = MainApp.myJDBC;

    @FXML
    private TextField luggageType;

    @FXML
    private TextField luggageBrand;

    @FXML
    private TextField primaryColor;

    @FXML
    private TextField secondaryColor;

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
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
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

    // Create new unique registrationnr
    private void newRegistrationNr(ActionEvent event) throws SQLException {
        Connection conn = db.getConnection();

        // select query for max registationNr
        String selectMaxRegNR = "SELECT MAX(registrationnr) FROM luggage";

        // insert query to create new registrationNr
        String insertNewRegNR = "INSERT INTO luggage (registrationnr, case_type) VALUES (?, 3)";
//        String INSERT_PICTURE = "insert into test(id, image01, image02, image03) values (?, ?, ?, ?)";

        // prepared statement
        PreparedStatement ps = null;
        try {
            // set autocommit false
            conn.setAutoCommit(false);

            // add query to prepared statement
            ps = conn.prepareStatement(selectMaxRegNR);

            // execute prepared statement
            ResultSet result = ps.executeQuery();

            // get results from the query
            if (result.next()) {
                registrationNr = result.getInt(1);

                // increment registrationNr by 1 for a new registrationNr
                registrationNr++;

                try {
                    // execute insert query for new registrationNr
                    // TODO: INSERT more luggage details    
                    PreparedStatement ps02 = null;
                    ps02 = conn.prepareStatement(insertNewRegNR);
                    ps02.setInt(1, registrationNr);
                    ps02.executeUpdate();
                    conn.commit();
                } finally {
                }

            }
        } finally {
            System.out.print(registrationNr);

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
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            //image01
            File file01 = new File(imageURL01);
            file01.toURI();
            fis01 = new FileInputStream(file01);

            //image02
            File file02 = new File(imageURL02);
            file02.toURI();
            fis02 = new FileInputStream(file02);

            //image03
            File file03 = new File(imageURL03);
            file03.toURI();
            fis03 = new FileInputStream(file03);

            //preparedstatement
            ps = conn.prepareStatement(INSERT_PICTURE);
            ps.setBinaryStream(1, fis01, (int) file01.length());
            ps.setBinaryStream(2, fis02, (int) file02.length());
            ps.setBinaryStream(3, fis03, (int) file03.length());
            ps.setInt(4, registrationNr);
            ps.executeUpdate();

            conn.commit();
        } finally {

        }
    }

    @FXML
    public void saveToDatabase(ActionEvent event) {
        // Image inside alert dialog resized to 65x50
        imageURL = this.getClass().getResource("/images/upload_button02.png").toString();
        Image image = new Image(imageURL, 100, 100, false, true);

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
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    savedConfirmation.setVisible(false);
                    saveImages.setVisible(true);
                    saveImages.setDisable(true);

                    try {
                        // create new registrationnr
                        newRegistrationNr(event);
                    } catch (SQLException ex) {
                        Logger.getLogger(BeschadigdeBagageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        // upload selected images
                        uploadImageQuery(event);
                    } catch (IOException | SQLException ex) {
                        Logger.getLogger(BeschadigdeBagageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            // clear all input field and set placeholder for images
            placeholderURL = this.getClass().getResource("/images/placeholder-600x400.png").toString();
            Image placeholder = new Image(placeholderURL);

            luggageType.clear();
            luggageBrand.clear();
            primaryColor.clear();
            secondaryColor.clear();
            notes.clear();
            image01.setImage(placeholder);
            image02.setImage(placeholder);
            image03.setImage(placeholder);

            // start the pause timer    
            pause.play();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // add items to combobox and set pre selection to"AMS"
        comboBox.getItems().addAll("AMS (Amsterdam Schiphol Airport)", "RTM (Rotterdam The Hague Airport)");
        comboBox.getSelectionModel().select("AMS (Amsterdam Schiphol Airport");

        // booleanbinding to check if textfields are empty
        // if so, disable the save button to prevent accidental uploads to the database
        BooleanBinding bb = luggageType.textProperty().isEmpty()
                .or(luggageBrand.textProperty().isEmpty())
                .or(primaryColor.textProperty().isEmpty())
                .or(secondaryColor.textProperty().isEmpty())
                .or(notes.textProperty().isEmpty());

        saveImages.disableProperty().bind(bb);

    }
}
