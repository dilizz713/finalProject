package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MaintenanceMainController {

    @FXML
    private Button btnDamage;

    @FXML
    private Button btnMaintenance;

    @FXML
    private AnchorPane maintenanceMainAnchorPane;

    @FXML
    void damageOnAction(ActionEvent event) {
        navigateTo("/view/vehicle-damage-view.fxml");
    }

    @FXML
    void maintenanceOnAction(ActionEvent event) {
        navigateTo("/view/vehicle-maintenance-records-view.fxml");
    }
    public void navigateTo(String fxmlPath){
        try{
            maintenanceMainAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            maintenanceMainAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

}
