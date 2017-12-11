package fys.luggagem;

import javafx.collections.ObservableList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Mees Sour
 */
public class ComboboxInformation {
    
    private final String[] englishColors = {"Black", "Blue", "Bluegreen", "Brown", 
        "Cream", "Darkblue", "Darkbrown", "Darkgray", "Darkgreen", "Darkred", 
        "Gray", "Green", "Lightblue", "Lightbrown", "Lightgray", "Lightgreen", 
        "Olive", "Orange", "Pink", "Purple", "Red", "Violet", "White", "Yellow"};
    
    private final String[] dutchColors = {"Zwart", "Blauw", "Blauwgroen", "Bruin", 
        "Crème", "Donkerblauw", "Donkerbruin", "Donkergrijs", "Donkergroen", "Donkerrood", 
        "Grijs", "Groen", "Lichtblauw", "Lightbruin", "Lightgrijs", "Lightgroen", 
        "Olijf", "Oranje", "Roze", "Paars", "Rood", "Violet", "White", "Yellow"};

    public String[] getEnglishColors() {
        return englishColors;
    }

    public String[] getDutchColors() {
        return dutchColors;
    }

}
