package fys.luggagem;

import fys.luggagem.models.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jordan van Beijnhem <jordan.van.beijnhem@hva.nl>
 */
public class NewCustomerFXMLController implements Initializable {

    private Customer customer = MainApp.getCustomer();
    private MyJDBC db = MainApp.myJDBC;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField prepositionField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField adresField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailAdresField;

    @FXML
    public void handleSaveAction() throws IOException, SQLException {
        customer.setFirstName(firstNameField.getText());
        customer.setPreposition(prepositionField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setAdres(adresField.getText());
        customer.setCity(cityField.getText());
        customer.setPostalCode(postalCodeField.getText());
        customer.setCountry(countryField.getText());
        customer.setPhoneNumber(phoneNumberField.getText());
        customer.setEmailAdres(emailAdresField.getText());
        setCustomer();
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/VerlorenBagageFXML.fxml"));

    }

    private void setCustomer() throws SQLException {
        Connection conn = db.getConnection();

        // create new unique customerNr and assing it to the customer object
        setCustomerNr();

        String insertCustomer = "INSERT INTO customer"
                + "(customernr, first_name, preposition, last_name, adres, city, postal_code, country, phone, email) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(insertCustomer);
            ps.setInt(1, customer.getCustomerNr());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getPreposition());
            ps.setString(4, customer.getLastName());
            ps.setString(5, customer.getAdres());
            ps.setString(6, customer.getCity());
            ps.setString(7, customer.getPostalCode());
            ps.setString(8, customer.getCountry());
            ps.setString(9, customer.getPhoneNumber());
            ps.setString(10, customer.getEmailAdres());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getSQLState() + "\nDifferent exception: " + e.getMessage());
        }
    }

    private void setCustomerNr() throws SQLException {
        int highestCustomerNr;

        Connection conn = db.getConnection();

        String getHighestCustomerNr = "SELECT MAX(customernr) FROM customer";

        PreparedStatement ps = null;
        try {
            // set autocommit false
            conn.setAutoCommit(false);

            // add query to prepared statement
            ps = conn.prepareStatement(getHighestCustomerNr);

            // execute prepared statement
            ResultSet result = ps.executeQuery();

            // get results from the query
            if (result.next()) {
                highestCustomerNr = result.getInt(1);

                // increment customernr
                highestCustomerNr++;
                customer.setCustomerNr(highestCustomerNr);
                System.out.print(customer.getCustomerNr());

            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
