package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

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
        dashboardAnchorPane.setOnKeyPressed(this::handleKeyPress);
        dashboardAnchorPane.requestFocus();
    }

    private void handleKeyPress(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER);
        SigninBtnOnAction(new ActionEvent(btnSignin,null));
    }
}
