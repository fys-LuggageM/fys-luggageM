package fys.luggagem;

import fys.luggagem.models.Customer;
import fys.luggagem.models.Data;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MatchingController implements Initializable {

    private Data data = MainApp.getData();
    private MyJDBC db = MainApp.myJDBC;
    private Customer customer = MainApp.getCustomer();

    private String labelnr;
    private String type;
    private String brand;
    private String primColor;
    private String secColor;

    @FXML
    private void getLuggageDetails(ActionEvent event) throws SQLException {
        Connection conn = db.getConnection();
        String luggageDetails = "SELECT labelnr, flightnr, luggage_type, brand, primary_color, secondary_color, airport_IATA FROM luggage WHERE registrationnr =?";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(luggageDetails);
            ps.setInt(1, 3);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                labelnr = rs.getString("labelnr");
                type = rs.getString("luggage_type");
                brand = rs.getString("brand");
                primColor = rs.getString("primary_color");
                secColor = rs.getString("secondary_color");
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
