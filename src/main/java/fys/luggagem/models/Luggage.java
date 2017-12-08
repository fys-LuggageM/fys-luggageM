package fys.luggagem.models;

import fys.luggagem.MainApp;
import fys.luggagem.MyJDBC;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author jordan
 */
public class Luggage {

    private MyJDBC myJDBC = MainApp.myJDBC;
    private Data data = MainApp.getData();

    private final String foundLuggageToDatabaseQuery = 
            "UPDATE luggage SET flightnr = ?, labelnr = ?, destination = ?, luggage_type = ?, brand = ?, "
            + "primary_color = ?, secondary_color = ?, customer_firstname = ?, customer_preposition = ?, "
            + "customer_lastname = ?, notes = ? "
            + "WHERE registrationnr = " + myJDBC.getLuggageRegistrationNr() + ";";
    private final String changesLogToDatabase = "INSERT INTO changes (changeid, Employee_code, Luggage_registrationnr) "
            + "VALUES (0, " + data.getEmployeeNr() + ", " + myJDBC.getLuggageRegistrationNr() + ");";


    private int registrationNr;
    private String flightNr;
    private String labelNr;
    private String destination;
    private String luggageType;
    private String brand;
    private String primaryColor;
    private String secondaryColor;
    private String notes;
    private int caseType;
    private String travellerName;
    private int caseStatus;
    private String IATA;
    private int customerNr;

    public Luggage() {

    }

    public Luggage(int registrationNr, String flightNr, String labelNr, String luggageType, String brand, String IATA) {
        this.registrationNr = registrationNr;
        this.flightNr = flightNr;
        this.labelNr = labelNr;
        this.luggageType = luggageType;
        this.brand = brand;
        this.IATA = IATA;
    }

    public int getRegistrationNr() {
        return registrationNr;
    }

    public void setRegistrationNr(int registrationNr) {
        this.registrationNr = registrationNr;
    }

    public String getFlightNr() {
        return flightNr;
    }

    public void setFlightNr(String flightNr) {
        this.flightNr = flightNr;
    }

    public String getLabelNr() {
        return labelNr;
    }

    public void setLabelNr(String labelNr) {
        this.labelNr = labelNr;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLuggageType() {
        return luggageType;
    }

    public void setLuggageType(String luggageType) {
        this.luggageType = luggageType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getCaseType() {
        return caseType;
    }

    public void setCaseType(int caseType) {
        this.caseType = caseType;
    }

    public String getTravellerName() {
        return travellerName;
    }

    public void setTravellerName(String travellerName) {
        this.travellerName = travellerName;
    }

    public int getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(int caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public int getCustomerNr() {
        return customerNr;
    }

    public void setCustomerNr(int customerNr) {
        this.customerNr = customerNr;
    }


    public void foundLuggageToDatabase(Customer customer) {
        try {
            PreparedStatement ps01 = null;
            myJDBC.getConnection().setAutoCommit(false);
            
            ps01 = myJDBC.getConnection().prepareStatement(foundLuggageToDatabaseQuery);
            
            ps01.setString(1, this.flightNr);
            ps01.setString(2, this.labelNr);
            ps01.setString(3, this.destination);
            ps01.setString(4, this.luggageType);
            ps01.setString(5, this.brand);
            ps01.setString(6, this.primaryColor);
            ps01.setString(7, this.secondaryColor);
            ps01.setString(8, customer.getFirstName());
            ps01.setString(9, customer.getPreposition());
            ps01.setString(10, customer.getLastName());
            ps01.setString(11, this.notes);
            
            System.out.println(this.registrationNr);
            System.out.println(ps01.toString());
            
            ps01.executeUpdate();
            myJDBC.executeUpdateQuery(changesLogToDatabase);
            myJDBC.getConnection().commit();        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Luggage> getMatchingLuggage(MyJDBC myJDBC, List<Luggage> luggageList) {
        luggageList.clear();
        Connection conn = myJDBC.getConnection();
        PreparedStatement ps = null;
        String matchingLuggage = "SELECT labelnr, luggage_type, brand, primary_color, secondary_color, case_status FROM luggage\n"
                + "    WHERE (labelnr = ? AND case_status = 1 AND registrationnr != ?) OR (luggage_type = ? AND brand = ? AND primary_color = ? AND secondary_color = ? AND case_status = 1 AND registrationnr != ?)";
        try {

            ps = conn.prepareStatement(matchingLuggage);
            ps.setString(1, getLabelNr());
            ps.setInt(2, myJDBC.getLuggageRegistrationNr());
            ps.setString(3, getLuggageType());
            ps.setString(4, getBrand());
            ps.setString(5, getPrimaryColor());
            ps.setString(6, getSecondaryColor());
            ps.setInt(7, myJDBC.getLuggageRegistrationNr());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Luggage luggage = new Luggage();
//                luggage.setRegistrationNr(rs.getInt(1));
                luggage.setLabelNr(rs.getString(1));
                luggage.setLuggageType(rs.getString(2));
                luggage.setBrand(rs.getString(3));
                luggage.setPrimaryColor(rs.getString(4));
                luggage.setSecondaryColor(rs.getString(5));
                luggageList.add(luggage);
            }
        } catch (SQLException sq) {
            myJDBC.error(sq);
        }

        return luggageList;
    }
}
