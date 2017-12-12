package fys.luggagem;

import static fys.luggagem.MainApp.data;
import javafx.collections.ObservableList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Mees Sour
 */
public class ComboboxInformation {
    
    private final static String COLOR_BLACK = data.getResourceBundle().getString("black");
    private final static String COLOR_BLUE = data.getResourceBundle().getString("blue");
    private final static String COLOR_BROWN = data.getResourceBundle().getString("brown");
    private final static String COLOR_CREAM = data.getResourceBundle().getString("cream");
    private final static String COLOR_DARKBLUE = data.getResourceBundle().getString("darkBlue");
    private final static String COLOR_DARKBROWN = data.getResourceBundle().getString("darkBrown");
    private final static String COLOR_DARKGRAY = data.getResourceBundle().getString("darkGray");
    private final static String COLOR_DARKGREEN = data.getResourceBundle().getString("darkGreen");
    private final static String COLOR_DARKRED = data.getResourceBundle().getString("darkRed");
    private final static String COLOR_GRAY = data.getResourceBundle().getString("gray");
    private final static String COLOR_GREEN = data.getResourceBundle().getString("green");
    private final static String COLOR_LIGHTBLUE = data.getResourceBundle().getString("lightBlue");
    private final static String COLOR_LIGHTBROWN = data.getResourceBundle().getString("lightBrown");
    private final static String COLOR_LIGHTGRAY = data.getResourceBundle().getString("lightGray");
    private final static String COLOR_LIGHTGREEN = data.getResourceBundle().getString("lightGreen");
    private final static String COLOR_OLIVE = data.getResourceBundle().getString("olive");
    private final static String COLOR_ORANGE = data.getResourceBundle().getString("orange");
    private final static String COLOR_PINK = data.getResourceBundle().getString("pink");
    private final static String COLOR_PURPLE = data.getResourceBundle().getString("purple");
    private final static String COLOR_RED = data.getResourceBundle().getString("red");
    private final static String COLOR_VIOLET = data.getResourceBundle().getString("violet");
    private final static String COLOR_WHITE = data.getResourceBundle().getString("white");
    private final static String COLOR_YELLOW = data.getResourceBundle().getString("yellow");
    
    private final static String SUITCASE = data.getResourceBundle().getString("suitcase");
    private final static String BAG = data.getResourceBundle().getString("bag");
    private final static String BAGPACK = data.getResourceBundle().getString("bagpack");
    private final static String BOX = data.getResourceBundle().getString("box");
    private final static String SPORTSBAG = data.getResourceBundle().getString("sportsBag");
    private final static String BUSINESSCASE = data.getResourceBundle().getString("businessCase");
    private final static String CASE = data.getResourceBundle().getString("case");
    private final static String OTHER = data.getResourceBundle().getString("other");
        
    private final static String[] ENGLISH_LOCATIONS = {"Belt-06", "Belt-05", "Belt-04", "Belt-03", 
        "Belt-02", "Belt-01", "Departure Hall", "Arrival hall", "Toilet"};
    
    private final static String[] DUTCH_LOCATIONS = {"Band-06", "Band-05", "Band-04", "Band-03", 
        "Band-02", "Band-01", "Vertrekhal", "Aankomsthal", "Toilet"};

    public static String[] getENGLISH_LUGGAGE_TYPES() {
        return ENGLISH_LUGGAGE_TYPES;
    }

    public static String[] getDUTCH_LUGGAGE_TYPES() {
        return DUTCH_LUGGAGE_TYPES;
    }

    public static String[] getENGLISH_LOCATIONS() {
        return ENGLISH_LOCATIONS;
    }

    public static String[] getDUTCH_LOCATIONS() {
        return DUTCH_LOCATIONS;
    }

    public static String getCOLOR_BLACK() {
        return COLOR_BLACK;
    }

    public static String getCOLOR_BLUE() {
        return COLOR_BLUE;
    }

    public static String getCOLOR_BROWN() {
        return COLOR_BROWN;
    }

    public static String getCOLOR_CREAM() {
        return COLOR_CREAM;
    }

    public static String getCOLOR_DARKBLUE() {
        return COLOR_DARKBLUE;
    }

    public static String getCOLOR_DARKBROWN() {
        return COLOR_DARKBROWN;
    }

    public static String getCOLOR_DARKGRAY() {
        return COLOR_DARKGRAY;
    }

    public static String getCOLOR_DARKGREEN() {
        return COLOR_DARKGREEN;
    }

    public static String getCOLOR_DARKRED() {
        return COLOR_DARKRED;
    }

    public static String getCOLOR_GRAY() {
        return COLOR_GRAY;
    }

    public static String getCOLOR_GREEN() {
        return COLOR_GREEN;
    }

    public static String getCOLOR_LIGHTBLUE() {
        return COLOR_LIGHTBLUE;
    }

    public static String getCOLOR_LIGHTBROWN() {
        return COLOR_LIGHTBROWN;
    }

    public static String getCOLOR_LIGHTGRAY() {
        return COLOR_LIGHTGRAY;
    }

    public static String getCOLOR_LIGHTGREEN() {
        return COLOR_LIGHTGREEN;
    }

    public static String getCOLOR_OLIVE() {
        return COLOR_OLIVE;
    }

    public static String getCOLOR_ORANGE() {
        return COLOR_ORANGE;
    }

    public static String getCOLOR_PINK() {
        return COLOR_PINK;
    }

    public static String getCOLOR_PURPLE() {
        return COLOR_PURPLE;
    }

    public static String getCOLOR_RED() {
        return COLOR_RED;
    }

    public static String getCOLOR_VIOLET() {
        return COLOR_VIOLET;
    }

    public static String getCOLOR_WHITE() {
        return COLOR_WHITE;
    }

    public static String getCOLOR_YELLOW() {
        return COLOR_YELLOW;
    }

}
