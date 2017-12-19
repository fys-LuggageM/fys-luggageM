package fys.luggagem;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
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
                } catch (NullPointerException e) {
                }
                
                try {
                    caseStatus = result.getInt("case_status");
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
                    Blob blob = result.getBlob("image01");
                    InputStream is = blob.getBinaryStream();
                    long blobLength = blob.length();

                    int pos = 1; // position is 1-based
                    int len = 10;
                    byte[] bytes = blob.getBytes(pos, len);

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
        
        if (handledCheckBox.isSelected()) {
            caseStatus = CASE_STATUS_INACTIEF;
        } else {
            caseStatus = CASE_STATUS_ACTIEF;
        }
        
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

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void commitChanges(ActionEvent event) {
        commitSQLchanges();
        closeStage(event);
    }

}
