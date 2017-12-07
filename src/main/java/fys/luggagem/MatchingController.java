package fys.luggagem;

import fys.luggagem.models.Customer;
import fys.luggagem.models.Data;
import fys.luggagem.models.Luggage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MatchingController implements Initializable {

    private Data data = MainApp.getData();
    private MyJDBC db = MainApp.myJDBC;
    private ObservableList<Luggage> luggageList = FXCollections.observableArrayList();

    private String labelnr;
    private String type;
    private String brand;
    private String primColor;
    private String secColor;
    
    private Luggage luggage;

    @FXML
    private TableView matchingTableview;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getLuggageDetails();
        
        matchingTableview.setItems(this.luggageList);

        luggage.getMatchingLuggage(db, this.luggageList);

        for (int cnr = 0; cnr < matchingTableview.getColumns().size(); cnr++) {
            TableColumn tc = (TableColumn) matchingTableview.getColumns().get(cnr);
            String propertyName = tc.getId();
            if (propertyName != null && !propertyName.isEmpty()) {
                tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                System.out.println("Attached column '" + propertyName + "' in tableview to matching attribute");
            }
        }
    }
    

  
    private void getLuggageDetails(){
        Connection conn = db.getConnection();
        String luggageDetails = "SELECT labelnr, flightnr, luggage_type, brand, primary_color, secondary_color, airport_IATA FROM luggage WHERE registrationnr = 6";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(luggageDetails);
//            ps.setInt(1, db.getRegNrLost());
//            ps.setInt(1, db.getRegNrDamaged());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                luggage.setLabelNr(rs.getString("labelnr"));
                luggage.setLuggageType(rs.getString("luggage_type"));
                luggage.setBrand(rs.getString("brand"));
                luggage.setPrimaryColor(rs.getString("primary_color"));
                luggage.setSecondaryColor(rs.getString("secondary_color"));
            }

            System.out.println("\n" + labelnr + "\n");
            System.out.println(type + "\n");
            System.out.println(brand + "\n");
            System.out.println(primColor + "\n");
            System.out.println(secColor + "\n");

        } catch (SQLException e) {
            System.err.println("deze query is hemeaal kaput" + e);
        }
    }

}
