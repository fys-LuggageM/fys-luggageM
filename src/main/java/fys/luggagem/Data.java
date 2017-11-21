/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys.luggagem;

import javafx.scene.Scene;

/**
 *
 * @author jordan
 */
public class Data {
    
    String name;
    Scene window;
    
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
}
