package fys.luggagem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ev1l0rd
 */
public class DatabaseFoundEditController implements Initializable {
    
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
    private TextField IATAField;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registrationNumber = MainApp.data.getLuggageRegistrationNumber();
        setCurrentLuggageFields();
    }

    private void setCurrentLuggageFields() {
        try {
            registrationNumberField.setText(Integer.toString(registrationNumber));
            String query = "SELECT * FROM luggage WHERE registrationnr =" + registrationNumber;
            ResultSet result = MainApp.myJDBC.executeResultSetQuery(query);
            while (result.next()){
                brandField.setText(result.getString("brand"));
                color1Field.setText(result.getString("primary_color"));
                colorField2.setText(result.getString("secondary_color"));
                IATAField.setText(result.getString("airport_IATA"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseFoundEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
