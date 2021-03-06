package fys.luggagem;

import fys.luggagem.models.Customer;
import fys.luggagem.models.Data;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static MyJDBC myJDBC;
    static Customer customer = new Customer();
    static Data data = new Data();
    Scene window;

    @Override
    public void start(Stage stage) throws Exception {
        String[] temp = Encryptor.encrypt("test");
        System.out.println(temp[0] + " " + temp[1]);
        createDatabase();
        myJDBC = new MyJDBC("luggagem");
        data.setStage(stage);
        setScene(this.getClass().getResource("/fxml/FXMLDocument.fxml"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static File selectFileToSave(String defaultFilename) {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Select file to save into");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File selectedFile = filechooser.showSaveDialog(data.getStage());
        if (selectedFile != null) {
            return selectedFile;
        }

        return null;
    }

    public static File selectFileToRead(String xlsx) {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Select file to read");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*.xlsx"));
        File selectedFile = filechooser.showOpenDialog(data.getStage());
        if (selectedFile != null) {
            return selectedFile;
        }
        return null;
    }

    public static void setScene(URL location) throws IOException {
        FXMLLoader loader = new FXMLLoader(location);
        loader.setResources(data.getResourceBundle());
        Parent root = (Parent) loader.load();
        if (data.getScene() == null) {
            Scene scene = new Scene(root);
            data.setScene(scene);
            data.getStage().setScene(scene);
            data.getStage().setFullScreen(true);
            data.getStage().setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            data.getStage().setWidth(data.getStage().getWidth());
            data.getStage().setHeight(data.getStage().getHeight());
            data.getStage().show();
        }
        data.getScene().setRoot(root);
    }

    public static void loadFXMLFile(URL fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(fxmlFileName);
            loader.setResources(data.getResourceBundle());
            Parent root = (Parent) loader.load();
            data.getWorkspace().getChildren().clear();
            data.getWorkspace().getChildren().add(root);
        } catch (IOException ex) {
            System.out.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static Data getData() {
        return data;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void createDatabase() {
        try {
            ArrayList<String> listOfDatabases = new ArrayList<>();
            MyJDBC myJDBC = new MyJDBC("sys");
            myJDBC.getConnection().createStatement();
            DatabaseMetaData meta = myJDBC.getConnection().getMetaData();
            ResultSet rs = meta.getCatalogs();
            while (rs.next()) {
                String database = rs.getString(1);
                listOfDatabases.add(database);
            }
            if (!listOfDatabases.contains("luggagem")) {
                myJDBC.createDatabase("luggagem");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
