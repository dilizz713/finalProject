package lk.ijse.gdse71.finalproject.controller;

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
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable{

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnMAintenance;

    @FXML
    private Button btnMilage;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnVehicle;

    @FXML
    private AnchorPane homeAnchorPane;

    private Button activeButton;


    @FXML
    void clickHomeButtonOnAction(ActionEvent event) {
        navigateTo("/view/home-menu-view.fxml");
    }


    @FXML
    void customerOnaction(ActionEvent event) {
        navigateTo("/view/customer-view.fxml");
    }

    @FXML
    void maintenanceOnAction(ActionEvent event) {
        navigateTo("/view/maintenance-main-view.fxml");
    }

    @FXML
    void mileageTrackingOnAction(ActionEvent event) {
        navigateTo("/view/mileage-tracking-view.fxml");
    }

    @FXML
    void paymentOnAction(ActionEvent event) {
        navigateTo("/view/payment-view.fxml");
    }

    @FXML
    void reservationOnAction(ActionEvent event) {
        navigateTo("/view/reservation-vehicle-view.fxml");
    }

    @FXML
    void vehicleOnAction(ActionEvent event) {
        navigateTo("/view/vehicle-view.fxml");
    }

    public void navigateTo(String fxmlPath){
        try{
            homeAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            homeAnchorPane.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/home-menu-view.fxml");
    }
}
