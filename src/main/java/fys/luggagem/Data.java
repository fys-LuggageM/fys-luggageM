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
    
    static String name;
    static Scene window;
    
    public static void setName(String n) {
        name = n;
    }
    
    public static String getName() {
        return name;
    }
    
    public static void setScene(Scene scene) {
        window = scene;
    }
    
    public static Scene getScene() {
        return window;
    }
}
