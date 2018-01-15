package fys.luggagem;

import fys.luggagem.models.Data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Tabish N
 */
public class RapportageController implements Initializable {

    Data data = MainApp.getData();
    private final MyJDBC DB = MainApp.myJDBC;

    @FXML
    private ComboBox<String> comboYear;
    @FXML
    private ComboBox<String> comboMonth;
    @FXML
    private TabPane tabel;
    @FXML
    private Tab tabVerloren;
    @FXML
    private Tab tabGevonden;
    @FXML
    private Tab tabBeschadigde;
    @FXML
    private Label exportLabel;
    @FXML
    private Button exportPdfButton;
    @FXML
    private Button loadingButton;
    @FXML
    private Label reportLabel;
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
    @FXML
    private PieChart gevondenPieChart;
    @FXML
    private PieChart beschadigdePieChart;
    @FXML
    private LineChart<?, ?> verlorenLineChart;
    @FXML
    private NumberAxis verlorenLineChartYAxis;
    @FXML
    private CategoryAxis verlorenLineChartXAxis;
    @FXML
    private LineChart<?, ?> gevondenLineChart;
    @FXML
    private NumberAxis gevondenLineChartYAxis;
    @FXML
    private CategoryAxis gevondenLineChartXAxis;
    @FXML
    private LineChart<?, ?> beschadigdeLineChart;
    @FXML
    private NumberAxis beschadigdeLineChartYAxis;
    @FXML
    private CategoryAxis beschadigdeLineChartXAxis;

    private ObservableList verlorenPieChartData;

    private ObservableList gevondenPieChartData;

    private ObservableList beschadigdePieChartData;

    private ResultSet resultSet;

    private String year;

    private String month;

//Localization strings
    private final String SCREEN_TITLE = data.getResourceBundle().getString("reports");

    private final String VERLOREN_CHART_TITLE = data.getResourceBundle().getString("lostChartTitle");

    private final String GEVONDEN_CHART_TITLE = data.getResourceBundle().getString("foundChartTitle");

    private final String BESCHADIGDE_CHART_TITLE = data.getResourceBundle().getString("damagedChartTitle");

    private final String REPORT_LABEL_TEXT = data.getResourceBundle().getString("reportLabel");

    private final String EXPORT_ALERT_TITLE = data.getResourceBundle().getString("exportAlertTitle");

    private final String EXPORT_ALERT_CONTENT = data.getResourceBundle().getString("exportAlertContent");

    private final String EXPORT_CANCEL = data.getResourceBundle().getString("exportCancel");

    private final String EXPORT_SAVE = data.getResourceBundle().getString("exportSave");

    private final String LUGGAGE_CHART_LEGEND = data.getResourceBundle().getString("luggageChartLegend");

    private final String AIRPORTS_CHART_LABEL = data.getResourceBundle().getString("airportsChartLabel");

    private final String GEVONDEN_CHART_LABEL = data.getResourceBundle().getString("foundChartLabel");

    private final String VERLOREN_CHART_LEGEND_LABEL = data.getResourceBundle().getString("lostChartLabel");

    private final String BESCHADIGDE_CHART_LABEL = data.getResourceBundle().getString("damagedChartLabel");

    private final String EXPORT_ALERT_YES_BUTTON = data.getResourceBundle().getString("exportAlertYesButton");

    private final String EXPORT_ALERT_NO_BUTTON = data.getResourceBundle().getString("exportAlertNoButton");

    // SQL Queries  
    private final String COMBO_YEAR_QUERY = "SELECT YEAR(luggage.date), "
            + "COUNT(*) c "
            + "FROM luggage "
            + "GROUP BY YEAR(luggage.date) "
            + "HAVING c > 0";

    private final String COMBO_MONTH = "SELECT MONTH(luggage.date), "
            + "COUNT(*) c "
            + "FROM luggage "
            + "WHERE YEAR(luggage.date) = ?"
            + "GROUP BY MONTH(luggage.date) "
            + "HAVING c > 0";

    private final String GROUP_RESULT_QUERY = (" " + "GROUP BY luggage.airport_IATA");

    private final String GEVONDEN_YEAR_RESULT_QUERY = "SELECT name, SUM(case_type = 1) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE YEAR(date) =";

    private final String VERLOREN_YEAR_RESULT_QUERY = "SELECT name, SUM(case_type = 2) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE YEAR(date) =";

    private final String BESCHADIGDE_YEAR_RESULT_QUERY = "SELECT name, SUM(case_type = 3) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE YEAR(date) =";

    private final String GEVONDEN_MONTH_RESULT_QUERY = "SELECT name, SUM(case_type = 1) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE (YEAR(date),MONTH(date)) =";

    private final String VERLOREN_MONTH_RESULT_QUERY = "SELECT name, SUM(case_type = 2) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE (YEAR(date),MONTH(date)) =";

    private final String BESCHADIGDE_MONTH_RESULT_QUERY = "SELECT name, SUM(case_type = 3) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE (YEAR(date),MONTH(date)) =";

    //This button creates the report label text and draws all the charts. It also makes the combobox comboMonths visible
    //when an item of the combobox comboYear has been selected.
    @FXML
    private void handleLoadingButtonAction(ActionEvent event) {
        System.out.println("Loading button pressed");

        if (comboYear.getValue() != null) {
            comboMonth.setVisible(true);
        }

        drawReportLabelText();
        drawFoundLuggageCharts();
        drawLostLuggageCharts();
        drawDamagedLuggageCharts();

        exportLabel.setText("");
    }

    //This method changes the report label text depending on the selected comboboxes
    private void drawReportLabelText() {
        if (comboYear.getValue() != null && comboMonth.getValue() == null) {
            reportLabel.setText(REPORT_LABEL_TEXT + " " + comboYear.getValue() + "?");
        } else if (comboYear.getValue() != null) {
            reportLabel.setText(REPORT_LABEL_TEXT + " " + comboMonth.getValue() + "/" + comboYear.getValue() + "?");
        }
    }

    //This method creates the linechart and the piechart of gevonden bagage
    private void drawFoundLuggageCharts() {
        if (tabGevonden.isSelected()) {
            populatePieChart(gevondenPieChart);
            gevondenPieChart.setVisible(true);

            gevondenLineChart.getData().clear();
            populateLineChart(gevondenLineChart);
            gevondenLineChart.setVisible(true);
            if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                gevondenLineChart.setTitle(GEVONDEN_CHART_TITLE + " " + comboYear.getValue());
            } else {
                gevondenLineChart.setTitle(GEVONDEN_CHART_TITLE + " " + comboMonth.getValue() + " "
                        + comboYear.getValue());

            }
        }
    }

    //This method creates the linechart and the piechart of verloren bagage
    private void drawLostLuggageCharts() {
        if (tabVerloren.isSelected()) {
            populatePieChart(verlorenPieChart);
            verlorenPieChart.setVisible(true);

            verlorenLineChart.getData().clear();
            populateLineChart(verlorenLineChart);
            verlorenLineChart.setVisible(true);
            if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                verlorenLineChart.setTitle(VERLOREN_CHART_TITLE + " " + comboYear.getValue());
            } else {
                verlorenLineChart.setTitle(VERLOREN_CHART_TITLE + " " + comboMonth.getValue() + " "
                        + comboYear.getValue());

            }
        }
    }

    //This method creates the linechart and the piechart of beschadigde bagage
    private void drawDamagedLuggageCharts() {
        if (tabBeschadigde.isSelected()) {
            populatePieChart(beschadigdePieChart);
            beschadigdePieChart.setVisible(true);

            beschadigdeLineChart.getData().clear();
            populateLineChart(beschadigdeLineChart);
            beschadigdeLineChart.setVisible(true);
            if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                beschadigdeLineChart.setTitle(BESCHADIGDE_CHART_TITLE + " " + comboYear.getValue());
            } else {
                beschadigdeLineChart.setTitle(BESCHADIGDE_CHART_TITLE + " " + comboMonth.getValue() + " "
                        + comboYear.getValue());

            }
        }
    }

    @FXML
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

    //This button when pressed runs the two methods below handleExportPdfButtonAction and creates an alertbox
    @FXML
    private void handleExportPdfButtonAction(ActionEvent event) throws IOException {
        System.out.println("Export Button Pressed");

        Alert alertPdf = new Alert(Alert.AlertType.CONFIRMATION);
        alertPdf.initOwner(data.getStage());
        alertPdf.setTitle(EXPORT_ALERT_TITLE);
        alertPdf.setHeaderText(EXPORT_ALERT_TITLE);
        alertPdf.setContentText(EXPORT_ALERT_CONTENT);

        ButtonType yesButton = new ButtonType(EXPORT_ALERT_YES_BUTTON);
        ButtonType noButton = new ButtonType(EXPORT_ALERT_NO_BUTTON);

        alertPdf.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alertPdf.showAndWait();
        if (result.get() == yesButton) {
            System.out.println("Export alertbox button pressed");
            File file = MainApp.selectFileToSave("*.pdf");

            if (file != null) {
                String filename = file.getAbsolutePath();
                PDFExport.makePdfTwoImage(filename, SCREEN_TITLE, createPieChartImage(), createLineChartImage());

                exportLabel.setText(EXPORT_SAVE + " " + "'" + filename + "'");
            } else {
                System.out.println("Save cancelled");
                exportLabel.setText(EXPORT_CANCEL);
            }
            
        } else {
            System.out.println("Cancel Alertbox button pressed");
            exportLabel.setText(EXPORT_CANCEL);
        }

    }

    //this method creates a screenshot of the selected tab's piechart
    private BufferedImage createPieChartImage() throws IOException {
        BufferedImage exportPieImage = null;
        if (tabGevonden.isSelected()) {
            WritableImage image = gevondenPieChart.snapshot(new SnapshotParameters(), null);

            exportPieImage = SwingFXUtils.fromFXImage(image, null);
        } else if (tabVerloren.isSelected()) {
            WritableImage image = verlorenPieChart.snapshot(new SnapshotParameters(), null);

            exportPieImage = SwingFXUtils.fromFXImage(image, null);
        } else if (tabBeschadigde.isSelected()) {
            WritableImage image = beschadigdePieChart.snapshot(new SnapshotParameters(), null);

            exportPieImage = SwingFXUtils.fromFXImage(image, null);
        }
        return exportPieImage;
    }

    //This method creates a screenshot of the selected tab's linechart
    private BufferedImage createLineChartImage() throws IOException {
        BufferedImage exportLineImage = null;
        if (tabGevonden.isSelected()) {
            WritableImage image = gevondenLineChart.snapshot(new SnapshotParameters(), null);

            exportLineImage = SwingFXUtils.fromFXImage(image, null);

        } else if (tabVerloren.isSelected()) {
            WritableImage image = verlorenLineChart.snapshot(new SnapshotParameters(), null);

            exportLineImage = SwingFXUtils.fromFXImage(image, null);

        } else if (tabBeschadigde.isSelected()) {
            WritableImage image = beschadigdeLineChart.snapshot(new SnapshotParameters(), null);

            exportLineImage = SwingFXUtils.fromFXImage(image, null);
        }
        return exportLineImage;
    }

    // Method the put the combomonth invisible and to fill the comboYear combobox. ComboMonth is turned visible when
    // the loading button is pressed
    private void setComboBoxController() {

        comboMonth.setVisible(false);

        fillComboYear();
    }

    //this method fills the combobox comboYear through an resultset query
    private void fillComboYear() {
        try {
            resultSet = DB.executeResultSetQuery(COMBO_YEAR_QUERY);

            while (resultSet.next()) {
                year = resultSet.getString("YEAR(luggage.date)");
                comboYear.getItems().addAll(
                        year
                );
            }
            comboYear.getEditor().getText();
        } catch (SQLException ex) {
            Logger.getLogger(RapportageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //this method fills the combobox comboMonth through an resultset query
    private void fillComboMonth() {
        try {
            Connection conn = DB.getConnection();
            PreparedStatement comboMonthQuery = conn.prepareStatement(COMBO_MONTH);
            comboMonthQuery.setString(1, comboYear.getValue());

            resultSet = comboMonthQuery.executeQuery();

            comboMonth.getItems().add(
                    null
            );

            while (resultSet.next()) {
                month = resultSet.getString("MONTH(luggage.date)");

                comboMonth.getItems().addAll(
                        month
                );

            }

        } catch (SQLException ex) {
            Logger.getLogger(RapportageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//this method fills all piecharts with data
    private void populatePieChart(PieChart chart) {
        gevondenPieChart.setVisible(false);
        verlorenPieChart.setVisible(false);
        beschadigdePieChart.setVisible(false);

        gevondenLineChart.setVisible(false);
        verlorenLineChart.setVisible(false);
        beschadigdeLineChart.setVisible(false);

        gevondenLineChartXAxis.setLabel(AIRPORTS_CHART_LABEL);
        verlorenLineChartXAxis.setLabel(AIRPORTS_CHART_LABEL);
        beschadigdeLineChartXAxis.setLabel(AIRPORTS_CHART_LABEL);

        gevondenLineChartYAxis.setLabel(GEVONDEN_CHART_LABEL);
        verlorenLineChartYAxis.setLabel(VERLOREN_CHART_LEGEND_LABEL);
        beschadigdeLineChartYAxis.setLabel(BESCHADIGDE_CHART_LABEL);

        gevondenPieChartData = FXCollections.observableArrayList();
        verlorenPieChartData = FXCollections.observableArrayList();
        beschadigdePieChartData = FXCollections.observableArrayList();

        try {

            if (comboYear.getValue() == null) {

                reportLabel.setText("");

            } else if (tabGevonden.isSelected()) {
                resultSet = DB.executeResultSetQuery(GEVONDEN_YEAR_RESULT_QUERY + comboYear.getValue()
                        + GROUP_RESULT_QUERY);

            } else if (tabVerloren.isSelected()) {
                resultSet = DB.executeResultSetQuery(VERLOREN_YEAR_RESULT_QUERY + comboYear.getValue()
                        + GROUP_RESULT_QUERY);

            } else if (tabBeschadigde.isSelected()) {
                resultSet = DB.executeResultSetQuery(BESCHADIGDE_YEAR_RESULT_QUERY + comboYear.getValue()
                        + GROUP_RESULT_QUERY);

            }

            if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                if (tabGevonden.isSelected()) {
                    resultSet = DB.executeResultSetQuery(GEVONDEN_MONTH_RESULT_QUERY
                            + "(" + comboYear.getValue() + "," + "'" + comboMonth.getValue() + "'" + ")"
                            + GROUP_RESULT_QUERY);
                } else if (tabVerloren.isSelected()) {
                    resultSet = DB.executeResultSetQuery(VERLOREN_MONTH_RESULT_QUERY
                            + "(" + comboYear.getValue() + "," + "'" + comboMonth.getValue() + "'" + ")"
                            + GROUP_RESULT_QUERY);
                } else if (tabBeschadigde.isSelected()) {
                    resultSet = DB.executeResultSetQuery(BESCHADIGDE_MONTH_RESULT_QUERY
                            + "(" + comboYear.getValue() + "," + "'" + comboMonth.getValue() + "'" + ")"
                            + GROUP_RESULT_QUERY);
                }
            }

            if (tabGevonden.isSelected()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int gevondenBagage = resultSet.getInt("SUM(case_type = 1)");

                    gevondenPieChartData.add(new PieChart.Data(name + " - " + gevondenBagage + " " + LUGGAGE_CHART_LEGEND,
                            gevondenBagage));

                    gevondenPieChart.setAnimated(true);
                    gevondenPieChart.setLegendSide(Side.LEFT);

                    if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                        gevondenPieChart.setTitle(GEVONDEN_CHART_TITLE + " " + comboYear.getValue());
                    } else if (comboYear.getValue() != null) {
                        gevondenPieChart.setTitle(GEVONDEN_CHART_TITLE + " " + comboMonth.getValue() + " "
                                + comboYear.getValue());
                    }

                    gevondenPieChart.setData(gevondenPieChartData);
                    gevondenPieChart.setLabelsVisible(false);

                }
            }
            if (tabVerloren.isSelected()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int verlorenBagage = resultSet.getInt("SUM(case_type = 2)");

                    verlorenPieChartData.add(new PieChart.Data(name + " - " + verlorenBagage + " " + LUGGAGE_CHART_LEGEND,
                            verlorenBagage));
                    verlorenPieChart.setAnimated(true);
                    verlorenPieChart.setLegendSide(Side.LEFT);

                    if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                        verlorenPieChart.setTitle(VERLOREN_CHART_TITLE + " " + comboYear.getValue());
                    } else if (comboYear.getValue() != null) {
                        verlorenPieChart.setTitle(VERLOREN_CHART_TITLE + " " + comboMonth.getValue() + " " + comboYear.getValue());
                    }

                    verlorenPieChart.setData(verlorenPieChartData);
                    verlorenPieChart.setLabelsVisible(false);

                }
            }

            if (tabBeschadigde.isSelected()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int beschadigdeBagage = resultSet.getInt("SUM(case_type = 3)");

                    beschadigdePieChartData.add(new PieChart.Data(name + " - " + beschadigdeBagage + " "
                            + LUGGAGE_CHART_LEGEND, beschadigdeBagage));
                    beschadigdePieChart.setAnimated(true);
                    beschadigdePieChart.setLegendSide(Side.LEFT);

                    if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                        beschadigdePieChart.setTitle(BESCHADIGDE_CHART_TITLE + " " + comboYear.getValue());
                    } else if (comboYear.getValue() != null) {
                        beschadigdePieChart.setTitle(BESCHADIGDE_CHART_TITLE + " " + comboMonth.getValue() + " "
                                + comboYear.getValue());
                    }

                    beschadigdePieChart.setData(beschadigdePieChartData);
                    beschadigdePieChart.setLabelsVisible(false);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RapportageController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    // this methods fills all linecharts with data
    private void populateLineChart(LineChart chart) {
        XYChart.Series gevondenSeries = new XYChart.Series<>();
        XYChart.Series verlorenSeries = new XYChart.Series<>();
        XYChart.Series beschadigdeSeries = new XYChart.Series<>();

        try {

            if (tabGevonden.isSelected()) {
                //data for gevonden bagage linechart
                if (comboYear.getValue() != null) {
                    resultSet = DB.executeResultSetQuery(GEVONDEN_YEAR_RESULT_QUERY + comboYear.getValue()
                            + GROUP_RESULT_QUERY);
                }

                if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                    resultSet = DB.executeResultSetQuery(GEVONDEN_MONTH_RESULT_QUERY
                            + "(" + comboYear.getValue() + "," + "'" + comboMonth.getValue() + "'" + ")"
                            + GROUP_RESULT_QUERY);
                }

                while (resultSet.next()) {
                    String naam = resultSet.getString("name");
                    int gevondenBagage = resultSet.getInt("SUM(case_type = 1)");

                    gevondenSeries.getData().add(new XYChart.Data(naam, gevondenBagage));
                }
            }
            if (tabVerloren.isSelected()) {
                //data for verloren bagage linechart
                if (comboYear.getValue() != null) {
                    resultSet = DB.executeResultSetQuery(VERLOREN_YEAR_RESULT_QUERY + comboYear.getValue()
                            + GROUP_RESULT_QUERY);

                }

                if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                    resultSet = DB.executeResultSetQuery(VERLOREN_MONTH_RESULT_QUERY
                            + "(" + comboYear.getValue() + "," + "'" + comboMonth.getValue() + "'" + ")"
                            + GROUP_RESULT_QUERY);
                }
                while (resultSet.next()) {
                    String naam = resultSet.getString("name");
                    int verlorenBagage = resultSet.getInt("SUM(case_type = 2)");

                    verlorenLineChartXAxis.setLabel(naam);
                    verlorenSeries.getData().add(new XYChart.Data(naam, verlorenBagage));
                }
            }
            if (tabBeschadigde.isSelected()) {
                //data for beschadigde bagage linechart
                if (comboYear.getValue() != null) {
                    resultSet = DB.executeResultSetQuery(BESCHADIGDE_YEAR_RESULT_QUERY + comboYear.getValue()
                            + GROUP_RESULT_QUERY);

                }
                if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                    resultSet = DB.executeResultSetQuery(BESCHADIGDE_MONTH_RESULT_QUERY
                            + "(" + comboYear.getValue() + "," + "'" + comboMonth.getValue() + "'" + ")"
                            + GROUP_RESULT_QUERY);
                }
                while (resultSet.next()) {
                    String naam = resultSet.getString("name");
                    int beschadigdeBagage = resultSet.getInt("SUM(case_type = 3)");

                    beschadigdeSeries.getData().add(new XYChart.Data(naam, beschadigdeBagage));
                }
            }
            if (tabGevonden.isSelected()) {
                gevondenLineChart.setAnimated(false);
                gevondenLineChart.getData().clear();
                gevondenLineChart.getData().add(gevondenSeries);
            } else if (tabVerloren.isSelected()) {
                verlorenLineChart.setAnimated(false);
                verlorenLineChart.getData().clear();
                verlorenLineChart.getData().add(verlorenSeries);

            } else if (tabBeschadigde.isSelected()) {
                beschadigdeLineChart.setAnimated(false);
                beschadigdeLineChart.getData().clear();
                beschadigdeLineChart.getData().add(beschadigdeSeries);

            }

        } catch (SQLException ex) {
            Logger.getLogger(RapportageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // This method clears the comboMonth and refills each it time another item is selected from comboYear
    @FXML
    private void comboYearChange(ActionEvent event) {
        comboMonth.getItems().clear();
        fillComboMonth();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setComboBoxController();

        //Booleanbinding enables loadingbutton when an item from the combobox is selected
        BooleanBinding chartLoadBinding = comboYear.valueProperty().isNull();
        loadingButton.disableProperty().bind(chartLoadBinding);

        //Booleanbinding enables the export button and the table when the report label is shown
        BooleanBinding exportBinding = reportLabel.textProperty().isEmpty();
        exportPdfButton.disableProperty().bind(exportBinding);
        tabel.disableProperty().bind(exportBinding);

    }

}
