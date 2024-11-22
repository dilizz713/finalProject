package lk.ijse.gdse71.finalproject.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeMenuController implements Initializable {
    @FXML
    public VBox homevBox;
    @FXML
    private AnchorPane homeMenuAnchorPane;

    @FXML
    private Label label45;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        label45.setStyle("-fx-font-size: 100px; -fx-font-family: \"Arial Bold\"; -fx-font-weight: bold; -fx-text-fill: white;-fx-effect: dropshadow(gaussian, darkred, 3, 2, 0, 5);");


    }

}
