/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys.luggagem.models;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author jordan
 */
public class Data {

    private String name;
    private int employeeNr;
    private Scene window;
    private String language;
    private String country;
    private Locale locale;
    private ResourceBundle resource;
    private Stage stage;
    private StackPane workspace;
    private int permissions;
    private String lastScene;
    private int luggageRegistrationNumber;
    private String timezone;

    public int getLuggageRegistrationNumber() {
        return luggageRegistrationNumber;
    }

    public void setLuggageRegistrationNumber(int luggageRegistrationNumber) {
        this.luggageRegistrationNumber = luggageRegistrationNumber;
    }

    public Data() {
        language = "en";
        country = "US";
        locale = new Locale(language, country);
        resource = ResourceBundle.getBundle("bundles.Bundle", locale);
    }

    public void setLastScene(String lastScene) {
        this.lastScene = lastScene;
    }

    public String getLastScene() {
        return lastScene;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setScene(Scene scene) {
        window = scene;
    }

    public Scene getScene() {
        return window;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public void setLocale() {
        locale = new Locale(language, country);
        resource = ResourceBundle.getBundle("bundles.Bundle", locale);
    }

    public ResourceBundle getResourceBundle() {
        return resource;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public StackPane getWorkspace() {
        return workspace;
    }

    public void setWorkspace(StackPane workspace) {
        this.workspace = workspace;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public int getEmployeeNr() {
        return employeeNr;
    }

    public void setEmployeeNr(int employeeNr) {
        this.employeeNr = employeeNr;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
