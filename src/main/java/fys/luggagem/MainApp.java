package fys.luggagem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainApp extends Application {

    static Data data = new Data();
    Scene window;

    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/FXMLDocument.fxml"));
//        loader.setResources(data.getResourceBundle());
//        Parent root = (Parent) loader.load();
//        Scene scene = new Scene(root);
//        data.setScene(scene);
//        stage.setScene(scene);
//        stage.setFullScreen(true);
//        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
//        stage.setWidth(stage.getWidth());
//        stage.setHeight(stage.getHeight());
//        stage.show();
        data.setStage(stage);
        setScene(this.getClass().getResource("/fxml/FXMLDocument.fxml"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static File selectFileToSave(String defaultFilename) {
        Stage stage = new Stage();
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Select file to save into");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File selectedFile = filechooser.showSaveDialog(stage);
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

    public static Data getData() {
        return data;
    }
}
