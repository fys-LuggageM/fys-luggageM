/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys.luggagem;

/**
 *
 * @author ev1l0rd
 */
public class Users {

    private String username;
    private String firstName;
    private String preposition = "";
    private String lastName;
    private String airport;
    private String active;
    private String permissions;

    public Users(String username, String firstName, String lastName, String airport, String active, String permissions) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.airport = airport;
        this.active = active;
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
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

    public String getAirport() {
        return airport;
    }

    public String getActive() {
        return active;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

}
