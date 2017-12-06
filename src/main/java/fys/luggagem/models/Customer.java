package fys.luggagem.models;

import fys.luggagem.MyJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Jordan van Beijnhem <jordan.van.beijnhem@hva.nl>
 */
public class Customer {

    private int customerNr;
    private String firstName;
    private String preposition;
    private String lastName;
    private String adres;
    private String city;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private String emailAdres;

    public Customer() {

    }

    public int getCustomerNr() {
        return customerNr;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPreposition() {
        return preposition;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAdres() {
        return adres;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public void setCustomerNr(int customerNr) {
        this.customerNr = customerNr;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAdres(String emailAdres) {
        this.emailAdres = emailAdres;
    }

    public boolean checkIfEmpty() {
        return customerNr == 0 && firstName == null && lastName == null && adres == null
                && city == null && postalCode == null && country == null && phoneNumber == null && emailAdres == null;
    }

    public void clear() {
        this.customerNr = 0;
        this.firstName = null;
        this.preposition = null;
        this.lastName = null;
        this.adres = null;
        this.city = null;
        this.postalCode = null;
        this.country = null;
        this.phoneNumber = null;
        this.emailAdres = null;
    }
    
    public static List<Customer> getAllCustomers(MyJDBC myJDBC, List<Customer> customerList) {
        customerList.clear();
        try {
            ResultSet rs = myJDBC.executeResultSetQuery("SELECT * FROM customer");
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerNr(rs.getInt(1));
                customer.setFirstName(rs.getString(2));
                customer.setPreposition(rs.getString(3));
                customer.setLastName(rs.getString(4));
                customer.setAdres(rs.getString(5));
                customer.setCity(rs.getString(6));
                customer.setPostalCode(rs.getString(7));
                customer.setCountry(rs.getString(8));
                customer.setPhoneNumber(rs.getString(9));
                customer.setEmailAdres(rs.getString(10));
                customerList.add(customer);
            }
        } catch (SQLException sq) {
            myJDBC.error(sq);
        }

        return customerList;
    }
}
