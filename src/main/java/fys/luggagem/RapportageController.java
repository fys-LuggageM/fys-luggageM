package fys.luggagem;

import fys.luggagem.models.Data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;

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

    private ObservableList verlorenPieChartData;

    private ObservableList gevondenPieChartData;

    private ObservableList beschadigdePieChartData;

    private ResultSet resultSet;

    private String year;

    private String month;

    private final int labelLineLength = 6;

//Localisatie Strings
    private final String screenTitle = data.getResourceBundle().getString("reports");

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

    private final String luggageChartLegend = data.getResourceBundle().getString("luggageChartLegend");

    // SQL Query's
    private final String comboYearQuery = "SELECT YEAR(luggage.date), "
            + "COUNT(*) c "
            + "FROM luggage "
            + "GROUP BY YEAR(luggage.date) "
            + "HAVING c > 0";

    private final String comboMonthQuery = "SELECT MONTH(luggage.date), "
            + "COUNT(*) c "
            + "FROM luggage "
            + "GROUP BY MONTH(luggage.date) "
            + "HAVING c > 0";

    private final String groupResultQuery = (" " + "GROUP BY luggage.airport_IATA");

    private final String gevondenYearResultQuery = "SELECT name, SUM(case_type = 1) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE YEAR(date) =";

    private final String verlorenYearResultQuery = "SELECT name, SUM(case_type = 2) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE YEAR(date) =";

    private final String beschadigdeYearResultQuery = "SELECT name, SUM(case_type = 3) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE YEAR(date) =";

    private final String gevondenMonthResultQuery = "SELECT name, SUM(case_type = 1) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE (YEAR(date),MONTH(date)) =";

    private final String verlorenMonthResultQuery = "SELECT name, SUM(case_type = 2) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE (YEAR(date),MONTH(date)) =";

    private final String beschadigdeMonthResultQuery = "SELECT name, SUM(case_type = 3) "
            + "FROM luggage "
            + "INNER JOIN airport ON luggage.airport_IATA=airport.IATA "
            + "WHERE (YEAR(date),MONTH(date)) =";

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

        if (tabGevonden.isSelected()) {
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
        } else if (tabVerloren.isSelected()) {
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
    private void handleCloseAction(ActionEvent event) throws IOException {
        MainApp.setScene(this.getClass().getResource("/fxml/HomeScreenFXML.fxml"));
    }

    @FXML
    private void handleExportPdfButtonAction(ActionEvent event) throws IOException {
//
//        if (pieChartData == null) {
//            exportLabel.setText(exportNoSelection);
//
//        } else {
//            createChartImage();
//        }
//else {
//
        Alert alertPdf = new Alert(Alert.AlertType.CONFIRMATION);
        alertPdf.initOwner(data.getStage());
        alertPdf.setTitle(exportAlertTitle);
        alertPdf.setHeaderText(exportAlertTitle);
        alertPdf.setContentText(exportAlertContent);
        Optional<ButtonType> result = alertPdf.showAndWait();

        if (result.get() == ButtonType.OK) {
            File file = MainApp.selectFileToSave("*.pdf");

            String filename = file.getAbsolutePath();
            PDFExport.makePdf(filename, screenTitle, createChartImage());

            exportLabel.setText(exportSave + " " + "'" + filename + "'");
        } else {
            exportLabel.setText(exportCancel);
        }
    }

    private BufferedImage createChartImage() throws IOException {
        BufferedImage exportImage = null;
        if (tabVerloren.isSelected()) {
            WritableImage image = verlorenAnchorPane.snapshot(new SnapshotParameters(), null);

            exportImage = SwingFXUtils.fromFXImage(image, null);
        } else if (tabGevonden.isSelected()) {
            WritableImage image = gevondenAnchorPane.snapshot(new SnapshotParameters(), null);

            exportImage = SwingFXUtils.fromFXImage(image, null);
        } else if (tabBeschadigde.isSelected()) {
            WritableImage image = beschadigdeAnchorPane.snapshot(new SnapshotParameters(), null);

            exportImage = SwingFXUtils.fromFXImage(image, null);
        }
        return exportImage;
    }

    private void comboBoxController() {

        fillComboYear();
        fillComboMonth();
    }

    private void fillComboYear() {
        try {
            resultSet = db.executeResultSetQuery(comboYearQuery);

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

    private void fillComboMonth() {
        try {
            resultSet = db.executeResultSetQuery(comboMonthQuery);

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

    private void populatePieChart(PieChart chart) {
        verlorenPieChart.setVisible(false);
        gevondenPieChart.setVisible(false);
        beschadigdePieChart.setVisible(false);

        verlorenLineChart.setVisible(false);
        gevondenLineChart.setVisible(false);
        beschadigdeLineChart.setVisible(false);

        verlorenPieChartData = FXCollections.observableArrayList();
        gevondenPieChartData = FXCollections.observableArrayList();
        beschadigdePieChartData = FXCollections.observableArrayList();

        try {

            if (comboYear.getValue() == null) {
                verlorenPieChart.setTitle(chooseAYear);
                gevondenPieChart.setTitle(chooseAYear);
                beschadigdePieChart.setTitle(chooseAYear);

                reportLabel.setText("");

            } else if (tabGevonden.isSelected()) {
                resultSet = db.executeResultSetQuery(gevondenYearResultQuery + comboYear.getValue()
                        + groupResultQuery);

            } else if (tabVerloren.isSelected()) {
                resultSet = db.executeResultSetQuery(verlorenYearResultQuery + comboYear.getValue()
                        + groupResultQuery);

            } else if (tabBeschadigde.isSelected()) {
                resultSet = db.executeResultSetQuery(beschadigdeYearResultQuery + comboYear.getValue()
                        + groupResultQuery);

            }

            if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                if (tabGevonden.isSelected()) {
                    resultSet = db.executeResultSetQuery(gevondenMonthResultQuery
                            + "(" + comboYear.getValue() + "," + comboMonth.getValue() + ")" + groupResultQuery);
                } else if (tabVerloren.isSelected()) {
                    resultSet = db.executeResultSetQuery(verlorenMonthResultQuery
                            + "(" + comboYear.getValue() + "," + comboMonth.getValue() + ")" + groupResultQuery);
                } else if (tabBeschadigde.isSelected()) {
                    resultSet = db.executeResultSetQuery(beschadigdeMonthResultQuery
                            + "(" + comboYear.getValue() + "," + comboMonth.getValue() + ")" + groupResultQuery);
                }
            }

            if (tabGevonden.isSelected()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int gevondenBagage = resultSet.getInt("SUM(case_type = 1)");

                    gevondenPieChartData.add(new PieChart.Data(name + " - " + gevondenBagage + " " + luggageChartLegend,
                            gevondenBagage));

                    gevondenPieChart.setAnimated(false);

                    if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                        gevondenPieChart.setTitle(gevondenChartTitle + " " + comboYear.getValue());
                    } else if (comboYear.getValue() != null) {
                        gevondenPieChart.setTitle(gevondenChartTitle + " " + comboMonth.getValue() + " "
                                + comboYear.getValue());
                    }

                    gevondenPieChart.setData(gevondenPieChartData);
                    gevondenPieChart.setLabelLineLength(labelLineLength);

                }
            }
            if (tabVerloren.isSelected()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int verlorenBagage = resultSet.getInt("SUM(case_type = 2)");

                    verlorenPieChartData.add(new PieChart.Data(name + " - " + verlorenBagage + " " + luggageChartLegend,
                            verlorenBagage));
                    verlorenPieChart.setAnimated(false);

                    if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                        verlorenPieChart.setTitle(verlorenChartTitle + " " + comboYear.getValue());
                    } else if (comboYear.getValue() != null) {
                        verlorenPieChart.setTitle(verlorenChartTitle + " " + comboMonth.getValue() + " " + comboYear.getValue());
                    }

                    verlorenPieChart.setData(verlorenPieChartData);
                    verlorenPieChart.setLabelLineLength(labelLineLength);

                }
            }

            if (tabBeschadigde.isSelected()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int beschadigdeBagage = resultSet.getInt("SUM(case_type = 3)");

                    beschadigdePieChartData.add(new PieChart.Data(name + " - " + beschadigdeBagage + " "
                            + luggageChartLegend, beschadigdeBagage));
                    beschadigdePieChart.setAnimated(false);

                    if (comboYear.getValue() != null && comboMonth.getValue() == null) {
                        beschadigdePieChart.setTitle(beschadigdeChartTitle + " " + comboYear.getValue());
                    } else if (comboYear.getValue() != null) {
                        beschadigdePieChart.setTitle(beschadigdeChartTitle + " " + comboMonth.getValue() + " "
                                + comboYear.getValue());
                    }

                    beschadigdePieChart.setData(beschadigdePieChartData);
                    beschadigdePieChart.setLabelLineLength(labelLineLength);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RapportageController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void populateLineChart(LineChart chart) {
        XYChart.Series gevondenSeries = new XYChart.Series<>();
        XYChart.Series verlorenSeries = new XYChart.Series<>();
        XYChart.Series beschadigdeSeries = new XYChart.Series<>();

        try {

            if (tabGevonden.isSelected()) {
                //data voor gevonden bagage linechart
                if (comboYear.getValue() != null) {
                    resultSet = db.executeResultSetQuery(gevondenYearResultQuery + comboYear.getValue()
                            + groupResultQuery);
                }

                if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                    resultSet = db.executeResultSetQuery(gevondenMonthResultQuery
                            + "(" + comboYear.getValue() + "," + comboMonth.getValue() + ")" + groupResultQuery);
                }

                while (resultSet.next()) {
                    String naam = resultSet.getString("name");
                    int gevondenBagage = resultSet.getInt("SUM(case_type = 1)");

                    gevondenSeries.getData().add(new XYChart.Data(naam, gevondenBagage));
                }
            }
            if (tabVerloren.isSelected()) {
                //data voor verloren bagage linechart
                if (comboYear.getValue() != null) {
                    resultSet = db.executeResultSetQuery(verlorenYearResultQuery + comboYear.getValue()
                            + groupResultQuery);

                }

                if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                    resultSet = db.executeResultSetQuery(verlorenMonthResultQuery
                            + "(" + comboYear.getValue() + "," + comboMonth.getValue() + ")" + groupResultQuery);
                }
                while (resultSet.next()) {
                    String naam = resultSet.getString("name");
                    int verlorenBagage = resultSet.getInt("SUM(case_type = 2)");

                    verlorenLineChartXAxis.setLabel(naam);
                    verlorenSeries.getData().add(new XYChart.Data(naam, verlorenBagage));
                }
            }
            if (tabBeschadigde.isSelected()) {
                //data voor beschadigde bagage linechart
                if (comboYear.getValue() != null) {
                    resultSet = db.executeResultSetQuery(beschadigdeYearResultQuery + comboYear.getValue()
                            + groupResultQuery);

                }
                if (comboYear.getValue() != null && comboMonth.getValue() != null) {
                    resultSet = db.executeResultSetQuery(beschadigdeMonthResultQuery
                            + "(" + comboYear.getValue() + "," + comboMonth.getValue() + ")" + groupResultQuery);
                }
                while (resultSet.next()) {
                    String naam = resultSet.getString("name");
                    int beschadigdeBagage = resultSet.getInt("SUM(case_type = 3)");

                    beschadigdeSeries.getData().add(new XYChart.Data(naam, beschadigdeBagage));
                }
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
