package fys.luggagem;

import java.io.File;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainApp extends Application {

    Scene window;
    Data data = new Data();
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/FXMLDocument.fxml"));
        Parent root = (Parent) loader.load();
        FXMLDocumentController controller = loader.getController();

        Scene scene = new Scene(root);
        window = scene;
        data.setScene(scene);
        controller.setData(data);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans
     * ignores main().
     *
     * @param args the command line arguments
     */
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
}
