package fys.luggagem;

import java.io.File;
import fys.luggagem.models.Data;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.paint.Paint;

/**
 * @author Mees Sour
 */
public class GevondenBagageController implements Initializable {

    private Data data = MainApp.getData();
    private String imageURL;
    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private List<ExcelImport> foundLuggageList;
    private int index = 0;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField registrationNumber;
    @FXML
    private DatePicker date;
    @FXML
    private TextField luggageType;
    @FXML
    private TextField brand;
    @FXML
    private TextField arrivedWithFlight;
    @FXML
    private TextField tag;
    @FXML
    private TextField locationFound;
    @FXML
    private TextField primaryColor;
    @FXML
    private TextField secondaryColor;
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
    private ComboBox airportFound;
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
        getNextRegistrationNumber();
        airportFound.getItems().addAll("AMS", "RTM");
        airportFound.getSelectionModel().select("AMS");
    }

    public void setFoundLuggageList(List<ExcelImport> list) {
        foundLuggageList = list;
        registrationNumber.setText(foundLuggageList.get(index).getRegistrationNr());
        time.setText(foundLuggageList.get(index).getTimeFound());
//        date.setValue(foundLuggageList.get(index).getDateFound());
        luggageType.setText(foundLuggageList.get(index).getLuggageType());
        brand.setText(foundLuggageList.get(index).getBrand());
        arrivedWithFlight.setText(foundLuggageList.get(index).getFlightNr());
        tag.setText(foundLuggageList.get(index).getLuggageTag());
        locationFound.setText(foundLuggageList.get(index).getLocationFound());
        primaryColor.setText(foundLuggageList.get(index).getPrimaryColor());
        secondaryColor.setText(foundLuggageList.get(index).getSecondaryColor());
        sizeHeigth.setText(foundLuggageList.get(index).getLuggageSizeHeigth());
        sizeWidth.setText(foundLuggageList.get(index).getLuggageSizeWidth());
        sizeDepth.setText(foundLuggageList.get(index).getLuggageSizeDepth());
        weight.setText(foundLuggageList.get(index).getLuggageWeight());
//        name.setText(foundLuggageList.get(index).getTravellerNameAndCityName());
        city.setText(foundLuggageList.get(index).getTravellerNameAndCityCity());
        comments.setText(foundLuggageList.get(index).getComments());
    }

    @FXML
    private void handleNextAction(ActionEvent event) {
        index = index + 1 >= foundLuggageList.size() ? index : index + 1;
        registrationNumber.setText(foundLuggageList.get(index).getRegistrationNr());
        time.setText(foundLuggageList.get(index).getTimeFound());
        luggageType.setText(foundLuggageList.get(index).getLuggageType());
        brand.setText(foundLuggageList.get(index).getBrand());
        arrivedWithFlight.setText(foundLuggageList.get(index).getFlightNr());
        tag.setText(foundLuggageList.get(index).getLuggageTag());
        locationFound.setText(foundLuggageList.get(index).getLocationFound());
        primaryColor.setText(foundLuggageList.get(index).getPrimaryColor());
        secondaryColor.setText(foundLuggageList.get(index).getSecondaryColor());
        sizeHeigth.setText(foundLuggageList.get(index).getLuggageSizeHeigth());
        sizeWidth.setText(foundLuggageList.get(index).getLuggageSizeWidth());
        sizeDepth.setText(foundLuggageList.get(index).getLuggageSizeDepth());
        weight.setText(foundLuggageList.get(index).getLuggageWeight());
//        name.setText(foundLuggageList.get(index).getTravellerNameAndCityName());
        city.setText(foundLuggageList.get(index).getTravellerNameAndCityCity());
        comments.setText(foundLuggageList.get(index).getComments());
    }

    @FXML
    private void handlePreviousAction(ActionEvent event) {
        index = index - 1 < 0 ? index : index - 1;
        registrationNumber.setText(foundLuggageList.get(index).getRegistrationNr());
        time.setText(foundLuggageList.get(index).getTimeFound());
        luggageType.setText(foundLuggageList.get(index).getLuggageType());
        brand.setText(foundLuggageList.get(index).getBrand());
        arrivedWithFlight.setText(foundLuggageList.get(index).getFlightNr());
        tag.setText(foundLuggageList.get(index).getLuggageTag());
        locationFound.setText(foundLuggageList.get(index).getLocationFound());
        primaryColor.setText(foundLuggageList.get(index).getPrimaryColor());
        secondaryColor.setText(foundLuggageList.get(index).getSecondaryColor());
        sizeHeigth.setText(foundLuggageList.get(index).getLuggageSizeHeigth());
        sizeWidth.setText(foundLuggageList.get(index).getLuggageSizeWidth());
        sizeDepth.setText(foundLuggageList.get(index).getLuggageSizeDepth());
        weight.setText(foundLuggageList.get(index).getLuggageWeight());
//        name.setText(foundLuggageList.get(index).getTravellerNameAndCityName());
        city.setText(foundLuggageList.get(index).getTravellerNameAndCityCity());
        comments.setText(foundLuggageList.get(index).getComments());
    }

    @FXML
    private void importExcel(ActionEvent event) throws IOException {
        System.out.println("You clicked Import Found Luggage!");

        File file = MainApp.selectFileToRead("*.xlsx");
        if (file != null) {
            String filename = file.getAbsolutePath();
            List<ExcelImport> foundLuggage = ExcelImport.importFoundLuggageFromExcel(filename);

            setFoundLuggageList(foundLuggage);

        } else {
            saveStatus.setText("Excel import has been cancelled.");
        }

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

//            getNextRegistrationNumber();
            String databaseRegistrationNumber = registrationNumber.getText();
            String databaseDate = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String databaseTime = time.getText();
            String databaseDateAndTime = databaseDate + " " + databaseTime;
            String databaseLuggageType = luggageType.getText();
            String databaseAirportName = airportFound.getValue().toString();
            String databaseBrand = brand.getText();
            String databaseFlightNumber = arrivedWithFlight.getText();
            String databaseLabelNumber = tag.getText();
            String databaseLocationFound = locationFound.getText();
            String databasePrimaryColor = primaryColor.getText();
            String databaseSecondaryColor = secondaryColor.getText();

            String databaseLuggageSize;
            String databaseLuggageSizeHeight = sizeHeigth.getText();
            String databaseLuggageSizeWidth = sizeWidth.getText();
            String databaseLuggageSizeDepth = sizeDepth.getText();

            if (databaseLuggageSizeHeight.matches("[0-9]+")
                    && databaseLuggageSizeWidth.matches("[0-9]+")
                    && databaseLuggageSizeDepth.matches("[0-9]+")) {
                databaseLuggageSize = (sizeHeigth.getText() + "x" + sizeWidth.getText() + "x" + sizeDepth.getText());
            } else {
                databaseLuggageSize = "";
            }

            String databaseWeight = weight.getText();
            String databaseTravellerFirstName = firstName.getText();
            String databaseTravellerInsertion = insertion.getText();
            String databaseTravellerLastName = lastName.getText();
            String databaseTravellerCity = city.getText();
            String databaseNotes = comments.getText();
            String databaseCaseType = "1";
            String databaseCaseStatus = "1";

            String queryLuggage = "INSERT INTO `luggagem`.`luggage` "
                    + "(`registrationnr`, `date`, `flightnr`, `labelnr`, `destination`, `luggage_type`, `brand`, `location_found`, `primary_color`, `secondary_color`, `size`, `weight`, `case_type`, `customer_firstname`, `customer_preposition`, `customer_lastname`, `case_status`, `airport_IATA`, `notes`)"
                    + "VALUES"
                    + "('" + databaseRegistrationNumber + "', '"
                    + databaseDateAndTime + "', '"
                    + databaseFlightNumber + "', '"
                    + databaseLabelNumber + "', '"
                    + databaseTravellerCity + "', '"
                    + databaseLuggageType + "', '"
                    + databaseBrand + "', '"
                    + databaseLocationFound + "', '"
                    + databasePrimaryColor + "', '"
                    + databaseSecondaryColor + "', '"
                    + databaseLuggageSize + "', '"
                    + databaseWeight + "', '"
                    + databaseCaseType + "', '"
                    + databaseTravellerFirstName + "', '"
                    + databaseTravellerInsertion + "', '"
                    + databaseTravellerLastName + "', '"
                    + databaseCaseStatus + "', '"
                    + databaseAirportName + "', '"                
                    + databaseNotes + "');";

            int doQueryLuggageTable = MainApp.myJDBC.executeUpdateQuery(queryLuggage);
            if (doQueryLuggageTable == -1) {
                saveStatus.setTextFill(Paint.valueOf("red"));
                saveStatus.setText(data.getResourceBundle().getString("saveFailed"));

            } else {
                saveStatus.setTextFill(Paint.valueOf("green"));
                saveStatus.setText(data.getResourceBundle().getString("saveSucces"));

                // clear all textfields.
                registrationNumber.clear();
                time.clear();
                luggageType.clear();
                brand.clear();
                arrivedWithFlight.clear();
                tag.clear();
                locationFound.clear();
                primaryColor.clear();
                secondaryColor.clear();
                sizeHeigth.clear();
                sizeWidth.clear();
                sizeDepth.clear();
                weight.clear();
                firstName.clear();
                insertion.clear();
                lastName.clear();
                city.clear();
                comments.clear();

                // Automatically set time and date after the clear
                time.setText(timeFormat.format(new Date()));
                date.setValue(LocalDate.now());
                getNextRegistrationNumber();

            }

        }

//        }
    }

    private void getNextRegistrationNumber() {

        if (true) {
            // Get next Registration Number
            String query = "SELECT MAX(registrationnr)"
                    + "FROM luggage";
            String result = MainApp.myJDBC.executeStringQuery(query);
            result = Integer.toString(Integer.parseInt(result) + 1);
            registrationNumber.setText(result);
        }

    }

}
