package hu.petrik.etlap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EtlapApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EtlapApp.class.getResource("main_etlap.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 515, 740);
        stage.setTitle("Étlap");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}