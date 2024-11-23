package lk.ijse.gdse71.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/dash-board.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280,726);
        stage.setTitle("");
        stage.setResizable(false);

        Image image = new Image(getClass().getResourceAsStream("/assets/dashboard.png"));
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}