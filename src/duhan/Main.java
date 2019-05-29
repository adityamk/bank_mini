package duhan;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @FXML
    private BorderPane borderpane;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Aplikasi Bank Mini SDIT Al Hamidiyyah");
        stage.setScene(scene);
        stage.setOnCloseRequest((event) -> {
            System.exit(0);
        });
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
