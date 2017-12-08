package fys.luggagem;

import fys.luggagem.models.Customer;
import fys.luggagem.models.Data;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Jordan van Beijnhem <jordan.van.beijnhem@hva.nl>
 */
public class ExistingCustomerFXMLController implements Initializable {

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private MyJDBC myJDBC = MainApp.myJDBC;
    private Data data = MainApp.getData();
    private Customer targetCustomer = MainApp.getCustomer();

    @FXML
    private TextField searchField;

    @FXML
    private TableView customerTableView;

    @FXML
    private void handleCloseAction() {
        MainApp.loadFXMLFile(this.getClass().getResource(data.getLastScene()));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerTableView.setItems(this.customerList);

        Customer.getAllCustomers(myJDBC, this.customerList);

        for (int cnr = 0; cnr < customerTableView.getColumns().size(); cnr++) {
            TableColumn tc = (TableColumn) customerTableView.getColumns().get(cnr);
            String propertyName = tc.getId();
            if (propertyName != null && !propertyName.isEmpty()) {
                tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                System.out.println("Attached column '" + propertyName + "' in tableview to matching attribute");
            }
        }

        FilteredList<Customer> filteredData = new FilteredList<>(customerList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getFirstName() != null && customer.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (customer.getPreposition() != null && customer.getPreposition().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (customer.getLastName() != null && customer.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getAdres() != null && customer.getAdres().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getCity() != null && customer.getCity().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getPostalCode() != null && customer.getPostalCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getCountry() != null && customer.getCountry().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getEmailAdres() != null && customer.getEmailAdres().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(customer.getCustomerNr()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(customerTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        customerTableView.setItems(sortedData);

        customerTableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    handleSelectAction();
                }
            });
            return row;
        });
    }

    private Customer getSelectedCustomer() {
        return (Customer) customerTableView.getSelectionModel().getSelectedItem();
    }

    private void handleSelectAction() {
        Customer selectedCustomer = getSelectedCustomer();
        targetCustomer.setCustomerNr(selectedCustomer.getCustomerNr());
        targetCustomer.setFirstName(selectedCustomer.getFirstName());
        targetCustomer.setPreposition(selectedCustomer.getPreposition());
        targetCustomer.setLastName(selectedCustomer.getLastName());
        targetCustomer.setAdres(selectedCustomer.getAdres());
        targetCustomer.setCity(selectedCustomer.getCity());
        targetCustomer.setPostalCode(selectedCustomer.getPostalCode());
        targetCustomer.setCountry(selectedCustomer.getCountry());
        targetCustomer.setPhoneNumber(selectedCustomer.getPhoneNumber());
        targetCustomer.setEmailAdres(selectedCustomer.getEmailAdres());
        MainApp.loadFXMLFile(this.getClass().getResource(data.getLastScene()));
    }

}
