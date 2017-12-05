package fys.luggagem;

import fys.luggagem.models.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    public void handleSaveAction() throws IOException {
        customer.setFirstName(firstNameField.getText());
        customer.setPreposition(prepositionField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setAdres(adresField.getText());
        customer.setCity(cityField.getText());
        customer.setPostalCode(postalCodeField.getText());
        customer.setCountry(countryField.getText());
        customer.setPhoneNumber(phoneNumberField.getText());
        customer.setEmailAdres(emailAdresField.getText());
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/VerlorenBagageFXML.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
