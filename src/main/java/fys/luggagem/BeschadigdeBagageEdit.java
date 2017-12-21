package fys.luggagem;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author Mees Sour
 */
public class BeschadigdeBagageEdit implements Initializable {

    @FXML
    private ImageView image01;
    @FXML
    private ImageView image02;
    @FXML
    private ImageView image03;
    @FXML
    private TextField registrationNumberField;
    @FXML
    private TextField airportFoundField;
    @FXML
    private TextField customerIDField;
    @FXML
    private TextArea notesField;
    @FXML
    private Button closeButton;
    @FXML
    private CheckBox handledCheckBox;
    
    private int registrationNumber;
    private int caseStatus;
    private final int CASE_STATUS_ACTIEF = 1;
    private final int CASE_STATUS_INACTIEF = 0;
    private String originalNotes;
    private int originalStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registrationNumber = MainApp.data.getLuggageRegistrationNumber();
        setCurrentLuggageInformation();
        setCurrentLuggageImages();
    }

    private void setCurrentLuggageInformation() {
        try {
            registrationNumberField.setText(Integer.toString(registrationNumber));
            String query = "SELECT * FROM luggage WHERE registrationnr=" + registrationNumber;
            ResultSet result = MainApp.myJDBC.executeResultSetQuery(query);
            while (result.next()) {

                try {
                    registrationNumberField.setText(result.getString("registrationnr"));
                } catch (NullPointerException e) {
                }

                try {
                    airportFoundField.setText(result.getString("airport_IATA"));
                } catch (NullPointerException e) {
                }

                try {
                    customerIDField.setText(result.getString("customer_customernr"));
                } catch (NullPointerException e) {
                }

                try {
                    notesField.setText(result.getString("notes"));
                    originalNotes = result.getString("notes");
                } catch (NullPointerException e) {
                }

                try {
                    caseStatus = result.getInt("case_status");
                    originalStatus = result.getInt("case_status");
                } catch (NullPointerException e) {
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (caseStatus == 0) {
            handledCheckBox.setSelected(true);
        }
    }

    private void setCurrentLuggageImages() {
        try {
            String query = "SELECT * FROM luggage_damaged WHERE Luggage_registrationnr=" + registrationNumber;
            ResultSet result = MainApp.myJDBC.executeResultSetQuery(query);
            if (result.next()) {

                try {
                    InputStream binaryStream = result.getBinaryStream("image01");
                    Image image = new Image(binaryStream);
                    image01.setImage(image);
                } catch (NullPointerException e) {
                }

                try {
                    InputStream binaryStream = result.getBinaryStream("image02");
                    Image image = new Image(binaryStream);
                    image02.setImage(image);
                } catch (NullPointerException e) {
                }

                try {
                    InputStream binaryStream = result.getBinaryStream("image03");
                    Image image = new Image(binaryStream);
                    image03.setImage(image);
                } catch (NullPointerException e) {
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void commitSQLchanges() {
        String query = "UPDATE luggage "
                + "SET notes=?, "
                + "case_status=? "
                + " WHERE registrationnr=?;";

        checkCaseStatus();

        try {
            PreparedStatement ps;
            Connection conn = MainApp.myJDBC.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            ps.setString(1, notesField.getText());
            ps.setInt(2, caseStatus);
            ps.setInt(3, registrationNumber);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.err.print("SQL error setfields: @@@@@@ " + e);
        }
    }

    private void registerAccountability() {
        checkCaseStatus();
        String newNotes = notesField.getText();
        if (!originalNotes.equals(newNotes) || originalStatus != caseStatus) {
            commitSQLchanges();
            deleteChanges();
            insertChanges();
        }
    }

    private void checkCaseStatus() {
        if (handledCheckBox.isSelected()) {
            caseStatus = CASE_STATUS_INACTIEF;
        } else {
            caseStatus = CASE_STATUS_ACTIEF;
        }
    }

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void commitChanges(ActionEvent event) {
        registerAccountability();
        closeStage(event);
    }

    private void insertChanges() {
        try {
            String query = "INSERT INTO changes "
                    + "(`Employee_code`, `Luggage_registrationnr`, `changeid`) "
                    + "VALUES (?, ?, ?);";
            PreparedStatement ps;
            Connection conn = MainApp.myJDBC.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            ps.setInt(1, MainApp.data.getEmployeeNr());
            ps.setInt(2, registrationNumber);
            ps.setInt(3, 1);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteChanges() {
        String query = "DELETE FROM changes "
                + "WHERE Luggage_registrationnr=?";
        try {
            PreparedStatement ps;
            Connection conn = MainApp.myJDBC.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            ps.setInt(1, registrationNumber);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
