package fys.luggagem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

    private ObservableList pieChartData;

    private ResultSet resultSet;

    private String year;

    private String month;

    private final String verlorenChartTitle = data.getResourceBundle().getString("lostChartTitle");

    private final String gevondenChartTitle = data.getResourceBundle().getString("foundChartTitle");

    private final String beschadigdeChartTitle = data.getResourceBundle().getString("damagedChartTitle");

    private final String reportLabelText = data.getResourceBundle().getString("reportLabel");

    private final String chooseAYear = data.getResourceBundle().getString("chooseAYear");

    private final String exportNoSelection = data.getResourceBundle().getString("exportNoSelection");

    private final String exportAlertTitle = data.getResourceBundle().getString("exportAlertTitle");

    private final String exportAlertContent = data.getResourceBundle().getString("exportAlertContent");

    private final String exportCancel = data.getResourceBundle().getString("exportCancel");

    private final String exportSave = data.getResourceBundle().getString("exportSave");

    private final MyJDBC db = new MyJDBC("luggagem");

    @FXML
    private void handleLoadingButtonAction(ActionEvent event) {
        if (comboYear.getValue() != null && comboMonth.getValue() == null) {
            reportLabel.setText(reportLabelText + " " + comboYear.getValue() + "?");
        } else if (comboYear.getValue() != null) {
            reportLabel.setText(reportLabelText + " " + comboMonth.getValue() + " " + comboYear.getValue() + "?");
        } else {
            reportLabel.setText("");
        }

        if (tabVerloren.isSelected()) {
            populatePieChart(verlorenPieChart);
            verlorenPieChart.setVisible(true);

            verlorenLineChart.getData().clear();
            populateLineChart(verlorenLineChart);
            verlorenLineChart.setVisible(true);

            if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                verlorenLineChart.setTitle(verlorenChartTitle + " " + comboYear.getValue());
            } else if (comboYear.getValue() != null) {
                verlorenLineChart.setTitle(verlorenChartTitle + " " + comboMonth.getValue() + " " + comboYear.getValue());
            } else {
                verlorenLineChart.setTitle(chooseAYear);
                verlorenLineChart.getData().clear();
            }

        } else if (tabGevonden.isSelected()) {
            populatePieChart(gevondenPieChart);
            gevondenPieChart.setVisible(true);

            gevondenLineChart.getData().clear();
            populateLineChart(gevondenLineChart);
            gevondenLineChart.setVisible(true);

            if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                gevondenLineChart.setTitle(gevondenChartTitle + " " + comboYear.getValue());
            } else if (comboYear.getValue() != null) {
                gevondenLineChart.setTitle(gevondenChartTitle + " " + comboMonth.getValue() + " " + comboYear.getValue());
            } else {
                gevondenLineChart.setTitle(chooseAYear);
                gevondenLineChart.getData().clear();

            }
        } else if (tabBeschadigde.isSelected()) {
            populatePieChart(beschadigdePieChart);
            beschadigdePieChart.setVisible(true);

            beschadigdeLineChart.getData().clear();
            populateLineChart(beschadigdeLineChart);
            beschadigdeLineChart.setVisible(true);

            if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                beschadigdeLineChart.setTitle(beschadigdeChartTitle + " " + comboYear.getValue());
            } else if (comboYear.getValue() != null) {
                beschadigdeLineChart.setTitle(beschadigdeChartTitle + " " + comboMonth.getValue() + " " + comboYear.getValue());
            } else {
                beschadigdeLineChart.setTitle(chooseAYear);
                beschadigdeLineChart.getData().clear();
            }
        }

        exportLabel.setText("");
    }

    @FXML
    private void handleExportPdfButtonAction(ActionEvent event) throws IOException {

        if (pieChartData == null) {
            exportLabel.setText(exportNoSelection);

        }
        
        
//else {
//
//            Alert alertPdf = new Alert(Alert.AlertType.CONFIRMATION);
//            alertPdf.initOwner(data.getStage());
//            alertPdf.setTitle(exportAlertTitle);
//            alertPdf.setHeaderText(exportAlertTitle);
//            alertPdf.setContentText(exportAlertContent);
//            Optional<ButtonType> result = alertPdf.showAndWait();
//
//            if (result.get() == ButtonType.OK) {
//                File file = MainApp.selectFileToSave("*.pdf");
//
//                String filename = file.getAbsolutePath();
//                PDFExport.makePdf(filename);
//
//                exportLabel.setText(exportSave + " " + "'" + filename + "'");
//            } else {
//                exportLabel.setText(exportCancel);
//            }
        
        }

        @FXML
        private void handleCloseAction
        (ActionEvent event) throws IOException {
            MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
        }

    

    private void comboBoxController() {

        try {
            resultSet = db.executeResultSetQuery("SELECT YEAR(date), COUNT(*) c FROM airport GROUP BY YEAR(date) HAVING c > 0");

            while (resultSet.next()) {
                year = resultSet.getString("YEAR(date)");
                comboYear.getItems().addAll(
                        year
                );
            }
            comboYear.getEditor().getText();
        } catch (SQLException ex) {
            Logger.getLogger(RapportageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            resultSet = db.executeResultSetQuery("SELECT MONTH(date), COUNT(*) c FROM airport GROUP BY MONTH(date) HAVING c > 0");

            comboMonth.getItems().add(
                    null
            );

            while (resultSet.next()) {
                month = resultSet.getString("MONTH(date)");

                comboMonth.getItems().addAll(
                        month
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(RapportageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void populatePieChart(PieChart chart) {
        verlorenPieChart.setVisible(false);
        gevondenPieChart.setVisible(false);
        beschadigdePieChart.setVisible(false);

        verlorenLineChart.setVisible(false);
        gevondenLineChart.setVisible(false);
        beschadigdeLineChart.setVisible(false);

        pieChartData = FXCollections.observableArrayList();

        try {
            //not null block
            if (comboYear.getValue() != null) {
                resultSet = db.executeResultSetQuery("SELECT name, timezone FROM airport where YEAR(date) ="
                        + comboYear.getValue());
            } else {
                verlorenPieChart.setTitle(chooseAYear);
                gevondenPieChart.setTitle(chooseAYear);
                beschadigdePieChart.setTitle(chooseAYear);

                reportLabel.setText("");
            }
            if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                resultSet = db.executeResultSetQuery("SELECT name, timezone FROM airport where (YEAR(date), MONTH(date)) = ("
                        + comboYear.getValue() + "," + comboMonth.getValue() + ")");
            }

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int timeZone = resultSet.getInt("timezone");

                pieChartData.add(new PieChart.Data(name, timeZone));

                if (tabVerloren.isSelected()) {
                    verlorenPieChart.setAnimated(false);

                    if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                        verlorenPieChart.setTitle(verlorenChartTitle + " " + comboYear.getValue());
                    } else if (comboYear.getValue() != null) {
                        verlorenPieChart.setTitle(verlorenChartTitle + " " + comboMonth.getValue() + " " + comboYear.getValue());
                    }

                    verlorenPieChart.setData(pieChartData);
                    verlorenPieChart.setLabelLineLength(10);

                } else if (tabGevonden.isSelected()) {
                    gevondenPieChart.setAnimated(false);

                    if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                        gevondenPieChart.setTitle(gevondenChartTitle + " " + comboYear.getValue());
                    } else if (comboYear.getValue() != null) {
                        gevondenPieChart.setTitle(gevondenChartTitle + " " + comboMonth.getValue() + " "
                                + comboYear.getValue());
                    }

                    gevondenPieChart.setData(pieChartData);
                    gevondenPieChart.setLabelLineLength(10);
                } else if (tabBeschadigde.isSelected()) {
                    beschadigdePieChart.setAnimated(false);

                    if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                        beschadigdePieChart.setTitle(beschadigdeChartTitle + " " + comboYear.getValue());
                    } else if (comboYear.getValue() != null) {
                        beschadigdePieChart.setTitle(beschadigdeChartTitle + " " + comboMonth.getValue() + " "
                                + comboYear.getValue());
                    }

                    beschadigdePieChart.setData(pieChartData);
                    beschadigdePieChart.setLabelLineLength(10);

                }

                System.out.printf("%s = %d,\n", name, timeZone);

            }
        } catch (SQLException ex) {
            Logger.getLogger(RapportageController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateLineChart(LineChart chart) {
        XYChart.Series verlorenSeries = new XYChart.Series<>();
        XYChart.Series gevondenSeries = new XYChart.Series<>();
        XYChart.Series beschadigdeSeries = new XYChart.Series<>();

        try {
            //data voor verloren linechart
            if (comboYear.getValue() != null) {
                resultSet = db.executeResultSetQuery("SELECT name, timezone FROM airport where YEAR(date) ="
                        + comboYear.getValue());

            }

            if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                resultSet = db.executeResultSetQuery("SELECT name, timezone FROM airport where (YEAR(date), MONTH(date)) = ("
                        + comboYear.getValue() + "," + comboMonth.getValue() + ")");
            }
            while (resultSet.next()) {
                String naam = resultSet.getString("name");
                int timeZone = resultSet.getInt("timezone");

                verlorenLineChartXAxis.setLabel(naam);
                verlorenSeries.getData().add(new XYChart.Data(naam, timeZone));
            }

            //data voor gevonden linechart
            if (comboYear.getValue() != null) {
                resultSet = db.executeResultSetQuery("SELECT name, timezone FROM airport where YEAR(date) ="
                        + comboYear.getValue());

            }

            if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                resultSet = db.executeResultSetQuery("SELECT name, timezone FROM airport where (YEAR(date), MONTH(date)) = ("
                        + comboYear.getValue() + "," + comboMonth.getValue() + ")");
            }
            while (resultSet.next()) {
                String naam = resultSet.getString("name");
                int timeZone = resultSet.getInt("timezone");

                gevondenSeries.getData().add(new XYChart.Data(naam, timeZone));
            }

            //data voor beschadigde linechart
            if (comboYear.getValue() != null) {
                resultSet = db.executeResultSetQuery("SELECT name, timezone FROM airport where YEAR(date) ="
                        + comboYear.getValue());

            }
            if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                resultSet = db.executeResultSetQuery("SELECT name, timezone FROM airport where (YEAR(date), MONTH(date)) = ("
                        + comboYear.getValue() + "," + comboMonth.getValue() + ")");
            }
            while (resultSet.next()) {
                String naam = resultSet.getString("name");
                int timeZone = resultSet.getInt("timezone");

                beschadigdeSeries.getData().add(new XYChart.Data(naam, timeZone));
            }

            if (tabVerloren.isSelected()) {
                verlorenLineChart.setAnimated(false);
                verlorenLineChart.getData().clear();
                verlorenLineChart.getData().add(verlorenSeries);
            } else if (tabGevonden.isSelected()) {
                gevondenLineChart.setAnimated(false);
                gevondenLineChart.getData().clear();
                gevondenLineChart.getData().add(gevondenSeries);

            } else if (tabBeschadigde.isSelected()) {
                beschadigdeLineChart.setAnimated(false);
                beschadigdeLineChart.getData().clear();
                beschadigdeLineChart.getData().add(beschadigdeSeries);

            }

        } catch (SQLException ex) {
            Logger.getLogger(RapportageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        comboBoxController();
    }
}
