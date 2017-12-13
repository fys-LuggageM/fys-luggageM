package fys.luggagem;

import static fys.luggagem.MainApp.data;
import java.io.File;
import fys.luggagem.models.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * @author Mees Sour
 */
public class GevondenBagageController implements Initializable {

    private Data data = MainApp.getData();
    private String imageURL;
    private MyJDBC db = MainApp.myJDBC;

    private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private String setInfo = "INSERT INTO luggage (registrationnr, date, flightnr, labelnr, destination, luggage_type, "
            + "brand, location_found, primary_color, secondary_color, size, weight, case_type, customer_firstname, "
            + "customer_preposition, customer_lastname, case_status, notes, airport_IATA) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private String setInfo2 = "UPDATE luggage SET flightnr = ?, labelnr = ?, destination = ?, luggage_type = ?, "
            + "brand = ?, location_found = ?, primary_color = ?, secondary_color = ?, size = ?, weight = ?, "
            + "customer_firstname = ?, customer_preposition = ?, customer_lastname = ?, case_status = ?, "
            + "airport_IATA = ?, notes = ? "
            + "WHERE registrationnr = ?";

    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private ObservableList<String> airportsList = FXCollections.observableArrayList();
    private List<ExcelImport> foundLuggageList;
    private int index = 0;
    private ObservableList<String> colorList = FXCollections.observableArrayList();
    private ObservableList<String> secondaryColorList = FXCollections.observableArrayList();
    private ObservableList<String> luggageTypeList = FXCollections.observableArrayList();
    private ObservableList<String> locationFoundList = FXCollections.observableArrayList();
    private final String CASE_STATUS_FOUND_LUGGAGE = "1";

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField registrationNumber;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox luggageType;
    @FXML
    private TextField brand;
    @FXML
    private TextField arrivedWithFlight;
    @FXML
    private TextField tag;
    @FXML
    private ComboBox<String> locationFound;
    @FXML
    private ComboBox<String> primaryColor;
    @FXML
    private ComboBox<String> secondaryColor;
    @FXML
    private TextField sizeHeigth;
    @FXML
    private TextField sizeWidth;
    @FXML
    private TextField sizeDepth;
    @FXML
    private TextField weight;
    @FXML
    private TextField city;
    @FXML
    private TextArea comments;
    @FXML
    private Label saveStatus;
    @FXML
    private ComboBox<String> airportFound;
    @FXML
    private TextField time;
    @FXML
    private TextField firstName;
    @FXML
    private TextField insertion;
    @FXML
    private TextField lastName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the local date in the date datepicker.
        date.setValue(LocalDate.now());
        // Set the time
        time.setText(timeFormat.format(new Date()));
        // Fill the airport dropdown
        setupAiportBox();
        setupColorCombobox();
        setupLuggageTypeCombobox();
        setupLocationFound();
        airportFound.getSelectionModel().selectFirst();
    }

    public void setFoundLuggageList(List<ExcelImport> list) {
        foundLuggageList = list;
        importedLuggageToDatabase(list);
    }

    @FXML
    private void importExcel(ActionEvent event) throws IOException {
        System.out.println("You clicked Import Found Luggage!");

        File file = MainApp.selectFileToRead("*.xlsx");
        if (file != null) {
            String filename = file.getAbsolutePath();
            List<ExcelImport> foundLuggage = ExcelImport.importFoundLuggageFromExcel(filename);

            importedLuggageToDatabase(foundLuggage);

        } else {
            saveStatus.setText("Excel import has been cancelled.");
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(data.getStage());
        alert.setTitle("Excel file imported!");
        String s = file.getName() + " has been imported!";
        alert.setHeaderText(s);
        alert.showAndWait();
    }

    @FXML
    private void save(ActionEvent event) {
        //Make the red line of text with the error disappear
        saveStatus.setText(data.getResourceBundle().getString("saveReset"));
        // Image inside alert dialog resized to 100x100
        imageURL = this.getClass().getResource("/images/upload_button02.png").toString();
        Image image = new Image(imageURL, 100, 100, false, true);

        //Create new alert dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);

        // Alert dialog setup
        alert.initOwner(data.getStage());
        alert.setGraphic(new ImageView(image));
        alert.setTitle(data.getResourceBundle().getString("foundLuggageTitle"));
        alert.setHeaderText(data.getResourceBundle().getString("alertBoxHeader"));
        alert.setContentText(data.getResourceBundle().getString("alertBoxContent"));

        Optional<ButtonType> result = alert.showAndWait();

        // When pressed OK on the alert dialog box
        if (result.get() == ButtonType.OK) {
            try {
                // create new registrationnr
                db.newRegnrFoundLuggage();
            } catch (SQLException ex) {
                Logger.getLogger(GevondenBagageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            setFields();
            clearFields();

            time.setText(timeFormat.format(new Date()));
            date.setValue(LocalDate.now());
            goToMatching();
        }

    }

    private void setFields() {
        // make strings of texts and values from the textfields and comboboxes
        String databaseDate = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String databaseTime = time.getText();
        String databaseDateAndTime = databaseDate + " " + databaseTime;
        String databaseAirportName = airportFound.getValue().toString();
        String databaseBrand = brand.getText();
        String databaseFlightNumber = arrivedWithFlight.getText();
        String databaseLabelNumber = tag.getText();
        String databaseWeight = weight.getText();
        String databaseTravellerFirstName = firstName.getText();
        String databaseTravellerInsertion = insertion.getText();
        String databaseTravellerLastName = lastName.getText();
        String databaseTravellerCity = city.getText();
        String databaseNotes = comments.getText();
        String databaseCaseStatus = CASE_STATUS_FOUND_LUGGAGE;      
        String databaseLuggageType;       
        if (luggageType.getValue() != null) {
             databaseLuggageType = luggageType.getValue().toString();
        } else {
            databaseLuggageType = "";
        }       
        String databaseLocationFound;       
        if (locationFound.getValue() != null) {
            databaseLocationFound = locationFound.getValue().toString();
        } else {
            databaseLocationFound = "";
        }      
        String databasePrimaryColor;
        String databaseSecondaryColor;
        if (primaryColor.getValue() != null) {
            databasePrimaryColor = primaryColor.getValue().toString();
        } else {
            databasePrimaryColor = "";
        }
        if (secondaryColor.getValue() != null) {
            databaseSecondaryColor = secondaryColor.getValue().toString();
        } else {
            databaseSecondaryColor = "";
        }       
        String databaseLuggageSize;
        String databaseLuggageSizeHeight = sizeHeigth.getText();
        String databaseLuggageSizeWidth = sizeWidth.getText();
        String databaseLuggageSizeDepth = sizeDepth.getText();
        if (databaseLuggageSizeHeight.matches("[0-9]+")
                && databaseLuggageSizeWidth.matches("[0-9]+")
                && databaseLuggageSizeDepth.matches("[0-9]+")) {
            databaseLuggageSize = (sizeHeigth.getText() + "x" 
                    + sizeWidth.getText() + "x" 
                    + sizeDepth.getText());
        } else {
            databaseLuggageSize = "";
        }
        // update the fields in the created row with a designated registration number
        Connection conn = db.getConnection();
        PreparedStatement ps = null;

        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(setInfo2);

            ps.setString(1, databaseFlightNumber);
            ps.setString(2, databaseLabelNumber);
            ps.setString(3, databaseTravellerCity);
            ps.setString(4, databaseLuggageType);
            ps.setString(5, databaseBrand);
            ps.setString(6, databaseLocationFound);
            ps.setString(7, databasePrimaryColor);
            ps.setString(8, databaseSecondaryColor);
            ps.setString(9, databaseLuggageSize);
            ps.setString(10, databaseWeight);
            ps.setString(11, databaseTravellerFirstName);
            ps.setString(12, databaseTravellerInsertion);
            ps.setString(13, databaseTravellerLastName);
            ps.setString(14, databaseCaseStatus);
            ps.setString(15, databaseAirportName);
            ps.setString(16, databaseNotes);
            ps.setInt(17, db.getLuggageRegistrationNr());
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            System.err.print("SQL error setfields: @@@@@@ " + e);
        }
    }

    private void goToMatching() {
        MainApp.loadFXMLFile(this.getClass().getResource("/fxml/MatchingFXML.fxml"));
    }

    private void importedLuggageToDatabase(List<ExcelImport> list) {

        Connection conn = db.getConnection();
        PreparedStatement ps = null;
        

        for (ExcelImport luggageList : list) {
            String name = luggageList.getTravellerNameAndCityName();

            String date = luggageList.getDateFound();
            String time = luggageList.getTimeFound();

            int start = name.indexOf(' ');
            int end = name.lastIndexOf(' ');
            String firstName = "";
            String middleName = "";
            String lastName = "";
            if (start >= 0) {
                firstName = name.substring(0, start);
                if (end > start) {
                    middleName = name.substring(start + 1, end);
                }
                lastName = name.substring(end + 1, name.length());
            }

            try {
                db.getNewEmptyFoundLuggageNr();
                conn.setAutoCommit(false);
                ps = conn.prepareStatement(setInfo);

                ps.setInt(1, db.getLuggageRegistrationNr());
                ps.setTimestamp(2, luggageList.getTimestamp());
                ps.setString(3, luggageList.getFlightNr());
                ps.setString(4, luggageList.getLuggageTag());
                ps.setString(5, luggageList.getTravellerNameAndCityCity());
                ps.setString(6, luggageList.getLuggageType());
                ps.setString(7, luggageList.getBrand());
                ps.setString(8, luggageList.getLocationFound());
                ps.setString(9, luggageList.getPrimaryColor());
                ps.setString(10, luggageList.getSecondaryColor());
                ps.setString(11, luggageList.getLuggageSize());
                ps.setString(12, luggageList.getLuggageWeight());
                ps.setInt(13, 1);
                ps.setString(14, firstName);
                ps.setString(15, middleName);
                ps.setString(16, lastName);
                ps.setInt(17, 1);
                ps.setString(18, luggageList.getComments());
                ps.setString(19, luggageList.getIATA());
                ps.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupAiportBox() {
        try {
            // Do an SQL query to get all airports
            String query = "SELECT IATA FROM airport;";
            ResultSet result = MainApp.myJDBC.executeResultSetQuery(query);
            // Loop over de resultset
            while (result.next()) {
                // Voeg de individuele results to aan de database
                airportsList.add(result.getString("IATA"));
            }
            // Set the items of the airportbox to the observable list
            airportFound.setItems(airportsList);
        } catch (SQLException ex) {
            Logger.getLogger(GevondenBagageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupColorCombobox() {
        colorList.addAll(ComboboxInformation.getCOLOR_BLACK(),
                ComboboxInformation.getCOLOR_BLUE(),
                ComboboxInformation.getCOLOR_BROWN(),
                ComboboxInformation.getCOLOR_CREAM(),
                ComboboxInformation.getCOLOR_DARKBLUE(),
                ComboboxInformation.getCOLOR_DARKBROWN(),
                ComboboxInformation.getCOLOR_DARKGRAY(),
                ComboboxInformation.getCOLOR_DARKGREEN(),
                ComboboxInformation.getCOLOR_DARKRED(),
                ComboboxInformation.getCOLOR_GRAY(),
                ComboboxInformation.getCOLOR_GREEN(),
                ComboboxInformation.getCOLOR_LIGHTBLUE(),
                ComboboxInformation.getCOLOR_LIGHTBROWN(),
                ComboboxInformation.getCOLOR_LIGHTGRAY(),
                ComboboxInformation.getCOLOR_LIGHTGREEN(),
                ComboboxInformation.getCOLOR_OLIVE(),
                ComboboxInformation.getCOLOR_ORANGE(),
                ComboboxInformation.getCOLOR_PINK(),
                ComboboxInformation.getCOLOR_PURPLE(),
                ComboboxInformation.getCOLOR_RED(),
                ComboboxInformation.getCOLOR_VIOLET(),
                ComboboxInformation.getCOLOR_WHITE(),
                ComboboxInformation.getCOLOR_YELLOW());

        primaryColor.setItems(colorList);
        secondaryColor.setItems(colorList);
    }

    private void setupLuggageTypeCombobox() {

        luggageTypeList.addAll(ComboboxInformation.getSUITCASE(),
                ComboboxInformation.getBAG(),
                ComboboxInformation.getBAGPACK(),
                ComboboxInformation.getBOX(),
                ComboboxInformation.getSPORTSBAG(),
                ComboboxInformation.getBUSINESSCASE(),
                ComboboxInformation.getCASE(),
                ComboboxInformation.getOTHER());

        luggageType.setItems(luggageTypeList);
    }

    private void setupLocationFound() {
        locationFoundList.addAll(ComboboxInformation.getBELT_01(),
                ComboboxInformation.getBELT_02(),
                ComboboxInformation.getBELT_03(),
                ComboboxInformation.getBELT_04(),
                ComboboxInformation.getBELT_05(),
                ComboboxInformation.getBELT_06(),
                ComboboxInformation.getDEPARTURE_HALL(),
                ComboboxInformation.getARRIVAL_HAL(),
                ComboboxInformation.getTOILET());
        locationFound.setItems(locationFoundList);
    }

    private void clearFields() {
        // clear all textfields.
        registrationNumber.clear();
        time.clear();
        brand.clear();
        arrivedWithFlight.clear();
        tag.clear();
        sizeHeigth.clear();
        sizeWidth.clear();
        sizeDepth.clear();
        weight.clear();
        firstName.clear();
        insertion.clear();
        lastName.clear();
        city.clear();
        comments.clear();
    }
}
