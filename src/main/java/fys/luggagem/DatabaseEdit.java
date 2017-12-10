package fys.luggagem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ev1l0rd
 */
public class DatabaseEdit implements Initializable {

    private int registrationNumber;

    @FXML
    private Button closeButton;
    @FXML
    private TextField registrationNumberField;
    @FXML
    private TextField color1Field;
    @FXML
    private TextField colorField2;

    @FXML
    private TextField brandField;
    @FXML
    private TextField destinationField;
    @FXML
    private TextField luggageHeightField;
    @FXML
    private TextField luggageWidthField;
    @FXML
    private TextField luggageDepthField;
    @FXML
    private TextField weightField;
    @FXML
    private TextArea notesField;
    @FXML
    private TextField locationFoundField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField betweenNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField flightNrField;
    @FXML
    private TextField luggageTypeField;
    @FXML
    private ComboBox<String> airportField;
    @FXML
    private TextField customerIDField;

    private ObservableList<String> Airports = FXCollections.observableArrayList();
    @FXML
    private TextField labelNrField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registrationNumber = MainApp.data.getLuggageRegistrationNumber();
        setCurrentLuggageFields();
        setupAiportBox();
    }

    private void setCurrentLuggageFields() {
        try {
            registrationNumberField.setText(Integer.toString(registrationNumber));
            String query = "SELECT * FROM luggage WHERE registrationnr=" + registrationNumber;
            ResultSet result = MainApp.myJDBC.executeResultSetQuery(query);
            while (result.next()) {
                try {
                    brandField.setText(result.getString("brand"));
                } catch (NullPointerException e) {
                }

                try {
                    color1Field.setText(result.getString("primary_color"));
                } catch (NullPointerException e) {
                }

                try {
                    colorField2.setText(result.getString("secondary_color"));
                } catch (NullPointerException e) {
                }

                //try {
                //IATAField.setText(result.getString("airport_IATA"));
                //} catch (NullPointerException e) {
                //}
                try {
                    destinationField.setText(result.getString("destination"));

                } catch (NullPointerException e) {
                }

                try {
                    String luggageSize = result.getString("size");
                    String[] luggageSizes = luggageSize.split("x");
                    luggageHeightField.setText(luggageSizes[0]);
                    luggageWidthField.setText(luggageSizes[1]);
                    luggageDepthField.setText(luggageSizes[2]);
                } catch (NullPointerException e) {
                }

                try {
                    weightField.setText(result.getString("weight"));
                } catch (NullPointerException e) {
                }

                try {
                    notesField.setText(result.getString("notes"));
                } catch (NullPointerException e) {
                }

                try {
                    locationFoundField.setText(result.getString("location_found"));
                } catch (NullPointerException e) {
                }

                try {
                    firstNameField.setText(result.getString("customer_firstname"));
                } catch (NullPointerException e) {
                }

                try {
                    betweenNameField.setText(result.getString("customer_preposition"));
                } catch (NullPointerException e) {
                }

                try {
                    lastNameField.setText(result.getString("customer_lastname"));
                } catch (NullPointerException e) {
                }

                try {
                    flightNrField.setText(result.getString("flightnr"));
                } catch (NullPointerException e) {
                }

                try {
                    labelNrField.setText(result.getString("labelnr"));
                } catch (NullPointerException e) {
                }

                try {
                    customerIDField.setText(result.getString("customer_customernr"));
                } catch (NullPointerException e) {
                }

                try {
                    luggageTypeField.setText(result.getString("luggage_type"));
                } catch (NullPointerException e) {
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            airportField.setItems(Airports);
        } catch (SQLException ex) {
            Logger.getLogger(AccountbeheerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void commitChanges(ActionEvent event) {
        String query = "UPDATE `luggagem`.`luggage` "
                + "SET `flightnr`='?', "
                + "`labelnr`='?', "
                + "`destination`='?', "
                + "`luggage_type`='?', "
                + "`brand`='?', "
                + "`location_found`='?', "
                + "`primary_color`='?', "
                + "`secondary_color`='?', "
                + "`notes`='?', `size`='?', "
                + "`weight`='?', "
                + "`customer_firstname`='?', "
                + "`customer_preposition`='?', "
                + "`customer_lastname`='?', "
                + "`airport_IATA`='?', "
                + "`customer_customernr`='?'"
                + " WHERE `registrationnr`='?';";
        String luggageSize = luggageHeightField.getText() + "x" + luggageWidthField.getText() + "x" + luggageDepthField.getText();
        
        try {
            PreparedStatement ps = null;
            Connection conn = MainApp.myJDBC.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            ps.setString(1, flightNrField.getText());
            ps.setString(2, labelNrField.getText());
            ps.setString(3, destinationField.getText());
            ps.setString(4, luggageTypeField.getText());
            ps.setString(5, brandField.getText());
            ps.setString(6, locationFoundField.getText());
            ps.setString(7, color1Field.getText());
            ps.setString(8, colorField2.getText());
            ps.setString(9, notesField.getText());
            ps.setString(10, luggageSize);
            ps.setString(11, weightField.getText());
            ps.setString(12, firstNameField.getText());
            ps.setString(13, betweenNameField.getText());
            ps.setString(14, lastNameField.getText());
            ps.setString(15, airportField.getValue());
            ps.setString(16, customerIDField.getText());
            ps.setInt(17, registrationNumber);
            ps.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            System.err.print("SQL error setfields: @@@@@@ " + e);
        }

        MainApp.myJDBC.executeUpdateQuery(query);
        closeStage(event);
    }

}
