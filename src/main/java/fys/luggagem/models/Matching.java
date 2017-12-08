/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys.luggagem.models;

/**
 *
 * @author Joris
 */
public class Matching {

    private String labelNr;
    private String luggageType;
    private String brand;
    private String primaryColor;
    private String secondaryColor;
    
    public Matching(){
        
    }

    public void setLabelNr(String labelNr) {
        this.labelNr = labelNr;
    }

    public void setLuggageType(String luggageType) {
        this.luggageType = luggageType;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getLabelNr() {
        return labelNr;
    }

    public String getLuggageType() {
        return luggageType;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

}
