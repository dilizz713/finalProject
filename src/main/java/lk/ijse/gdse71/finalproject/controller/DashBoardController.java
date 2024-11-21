package lk.ijse.gdse71.finalproject.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    @FXML
    public Label topicLabel;
    @FXML
    private Button btnSignin;

    @FXML
    private AnchorPane dashboardAnchorPane;

    @FXML
    private ImageView dashboardImage;

    @FXML
    void SigninBtnOnAction(ActionEvent event) {
        navigateTo("/view/login-page.fxml");
    }

    public void navigateTo(String fxmlPath){
        try{
            dashboardAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            dashboardAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autoTypeText(topicLabel, "Auto Rent Pro");
        dashboardAnchorPane.setOnKeyPressed(this::handleKeyPress);
        dashboardAnchorPane.requestFocus();
    }

    private void autoTypeText(Label topicLabel, String text) {
        final StringBuilder displayedText = new StringBuilder();
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(100), // Typing speed
                        event -> {
                            if (displayedText.length() < text.length()) {
                                displayedText.append(text.charAt(displayedText.length()));
                                topicLabel.setText(displayedText.toString());
                            }
                        }
                )
        );
        timeline.setCycleCount(text.length());
        timeline.play();

    }

    private void handleKeyPress(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER);
        SigninBtnOnAction(new ActionEvent(btnSignin,null));
    }
}
