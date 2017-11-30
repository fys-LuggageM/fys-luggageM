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
class Users {

    private String username;
    private String firstName;
    private String preposition = "";
    private String lastName;
    private String airport;
    private String active;
    private String permissions;

    public Users(String username, String firstName, String lastName, String airport, String active, String permissions) {
        this.username = username;
        System.out.println(this.username);
        this.firstName = firstName;
        System.out.println(this.firstName);
        this.lastName = lastName;
        System.out.println(this.lastName);
        this.airport = airport;
        System.out.println(this.airport);
        this.active = active;
        System.out.println(this.active);
        this.permissions = permissions;
        System.out.println(this.permissions);
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
