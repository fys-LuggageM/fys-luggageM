package fys.luggagem;

import javafx.collections.ObservableList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Mees Sour
 */
public class ComboboxInformation {
    
    private final static String[] ENGLISH_COLORS = {"Black", "Blue", "Bluegreen", "Brown", 
        "Cream", "Darkblue", "Darkbrown", "Darkgray", "Darkgreen", "Darkred", 
        "Gray", "Green", "Lightblue", "Lightbrown", "Lightgray", "Lightgreen", 
        "Olive", "Orange", "Pink", "Purple", "Red", "Violet", "White", "Yellow"};
    
    private final static String[] DUTCH_COLORS = {"Zwart", "Blauw", "Blauwgroen", "Bruin", 
        "Crème", "Donkerblauw", "Donkerbruin", "Donkergrijs", "Donkergroen", "Donkerrood", 
        "Grijs", "Groen", "Lichtblauw", "Lightbruin", "Lightgrijs", "Lightgroen", 
        "Olijf", "Oranje", "Roze", "Paars", "Rood", "Violet", "White", "Yellow"};
    
    private final static String[] ENGLISH_LUGGAGE_TYPES = {"Suitcase", "Bag", "Bagpack", "Box", 
        "Sports Bag", "Sports Bag", "Business Case", "Case", "Other"};
    
    private final static String[] DUTCH_LUGGAGE_TYPES = {"Koffer", "Tas", "Rugzak", "Doos", 
        "Sporttas", "Sports Bag", "Business Case", "Case", "Other"};
    
    private final static String[] ENGLISH_LOCATIONS = {"Belt-06", "Belt-05", "Belt-04", "Belt-03", 
        "Belt-02", "Belt-01", "Departure Hall", "Arrival hall", "Toilet"};
    
    private final static String[] DUTCH_LOCATIONS = {"Band-06", "Band-05", "Band-04", "Band-03", 
        "Band-02", "Band-01", "Vertrekhal", "Aankomsthal", "Toilet"};

    public static String[] getENGLISH_COLORS() {
        return ENGLISH_COLORS;
    }

    public static String[] getDUTCH_COLORS() {
        return DUTCH_COLORS;
    }

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

}
