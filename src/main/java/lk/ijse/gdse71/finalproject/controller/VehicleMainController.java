package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;



public class VehicleMainController {

    @FXML
    private AnchorPane VehicleMainAnchorPane;

    @FXML
    void navigateToBusUI(ActionEvent event) {
        navigateTo();
    }

    @FXML
    void navigateToCarUI(ActionEvent event) {
        navigateTo();
    }

    @FXML
    void navigateToLuxaryCarUI(ActionEvent event) {
        navigateTo();
    }

    @FXML
    void navigateToTrucksUI(ActionEvent event) {
        navigateTo();
    }

    @FXML
    void navigateToVansUI(ActionEvent event) {
        navigateTo();
    }
    public void navigateTo(){

        try{
            VehicleMainAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/vehicle-view.fxml"));
            VehicleMainAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

}
