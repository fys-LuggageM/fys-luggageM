package fys.luggagem.models;

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
        this.customerNr = 1;
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
}
