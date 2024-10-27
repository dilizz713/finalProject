package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardController {

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

}
