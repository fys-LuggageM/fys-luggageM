/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys.luggagem;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jordan
 */
public class Data {
    
    private String name;
    private Scene window;
    private String language;
    private String country;
    private Locale locale;
    private ResourceBundle resource;
    private Stage stage;
    
    public Data() {
        language = "en";
        country = "US";
        locale = new Locale(language, country);
        resource = ResourceBundle.getBundle("bundles.Bundle", locale);
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
}
