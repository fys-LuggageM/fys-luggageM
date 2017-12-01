package fys.luggagem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Tabish N
 */
public class RapportageController implements Initializable {

    Data data = MainApp.getData();

    @FXML
    private ComboBox<String> comboYear;

    @FXML
    private ComboBox<String> comboDay;

    @FXML
    private ComboBox<String> comboMonth;

    @FXML
    private TabPane tabel;

    @FXML
    private Tab tabVerloren;

    @FXML
    private Tab tabGevonden;

    @FXML
    private Tab tabBeschadigd;

    @FXML
    private Label exportLabel;

    @FXML
    private Button exportPdfButton;
    @FXML
    private Button exportExcelButton;
    @FXML
    private Button loadingButton;
    @FXML
    private Label verlorenTabLabel;
    @FXML
    private TextField reportSearchField;
    @FXML
    private Label titleLabel;
    @FXML
    private AnchorPane verlorenAnchorPane;
    @FXML
    private AnchorPane gevondenAnchorPane;
    @FXML
    private AnchorPane beschadigdeAnchorPane;
    @FXML
    private PieChart verlorenPieChart;

    String reportSearchFieldInput;

// db is testdatabase, wachten op echte database, als je AirlineDemo in je localhost hebt dan werkt die 
//    MyJDBC db = new MyJDBC("AirlineDemo");
    @FXML
    private void handleLoadingButtonAction(ActionEvent event) {
        if (reportSearchField.getText().trim().isEmpty()) {
            System.out.println("data geladen!");
            verlorenTabLabel.setText("Wilt u data exporteren van " + comboDay.getValue() + " "
                    + comboMonth.getValue() + " " + comboYear.getValue() + "?");
        } else {
            reportSearchFieldInput = reportSearchField.getText();
            verlorenTabLabel.setText("Wilt u data importeren van " + reportSearchFieldInput + "?");
        }
        populatePieChart(verlorenPieChart);
    }

    @FXML
    private void handleExportPdfButtonAction(ActionEvent event) throws IOException {

        if (verlorenTabLabel == exportLabel) {
            exportLabel.setText("U heeft geen data gekozen om te exporteren!");

        } else {
            Alert alertPdf = new Alert(Alert.AlertType.CONFIRMATION);
            alertPdf.setTitle("Data exporteren naar PDF");
            alertPdf.setHeaderText("Data exporteren naar PDF");
            alertPdf.setContentText("Wilt u de data exporteren naar een PDF-bestand?");
            Optional<ButtonType> result = alertPdf.showAndWait();

            if (result.get() == ButtonType.OK) {
                File file = MainApp.selectFileToSave("*.pdf");

                String filename = file.getAbsolutePath();
                PDFExport.makePdf(filename);

                exportLabel.setText("Uw export is opgeslagen in '" + filename + "'");
            } else {
                exportLabel.setText("Uw export is geannuleerd");
            }
        }
    }

    @FXML
    private void handleExportExcelButtonAction(ActionEvent event) {

    }

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxController();
    }

    private void comboBoxController() {
        comboYear.getItems().addAll(
                "2000",
                "2001",
                "2002",
                "2003",
                "2004",
                "2005",
                "2006",
                "2007",
                "2008",
                "2009",
                "2010",
                "2011",
                "2012",
                "2013",
                "2014",
                "2015",
                "2016",
                "2017",
                "2018"
        );

        comboYear.getEditor().getText();

        comboMonth.getItems().addAll(
                "Januari",
                "Februari",
                "Maart",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Augustus",
                "September",
                "October",
                "November",
                "December"
        );
        comboMonth.getEditor().getText();

        comboDay.getItems().addAll(
                "1",
                "2",
                "10",
                "11",
                "20",
                "21",
                "30",
                "31"
        );
        comboDay.getEditor().getText();
    }

    private void populatePieChart(PieChart chart) {
//        try {
//            verlorenPieChart.setAnimated(false);
//            verlorenPieChart.setTitle(reportSearchFieldInput);
//
//            ResultSet resultSet = db.executeResultSetQuery("SELECT Name, TimeZone, Country FROM Airport");
//            List airportNameList = new ArrayList();
//            List airportTimeZoneList = new ArrayList();
//            while (resultSet.next()) {
//                String name = resultSet.getString("Name");
//                int timeZone = resultSet.getInt("TimeZone");
//                airportNameList.add(resultSet.getString("Name"));
//                airportTimeZoneList.add(resultSet.getInt("TimeZone"));
//                String country = resultSet.getString("Country");
//
//                ObservableList<PieChart.Data> pieChartData
//                        = FXCollections.observableArrayList(
//                                new PieChart.Data(resultSet.getString("Name"), resultSet.getInt("TimeZone")));
//
//                verlorenPieChart.setData(pieChartData);
//                verlorenPieChart.setLabelLineLength(10);
//                System.out.printf("%s = %d, %s\n", name, timeZone, country);
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(RapportageController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("piechart");
    }

}
