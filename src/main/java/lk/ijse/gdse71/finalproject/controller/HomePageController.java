package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnDriver;

    @FXML
    private Button btnInvoice;

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
    private AnchorPane homeAnchorPAne;

    @FXML
    private ImageView homeImage;

    @FXML
    void customerOnaction(ActionEvent event) {
        navigateTo("/view/customer-view.fxml");
    }

    @FXML
    void driverOnAction(ActionEvent event) {
        navigateTo("/view/driver-view.fxml");
    }

    @FXML
    void invoiceOnAction(ActionEvent event) {

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
        navigateTo("/view/reservation-view.fxml");
    }

    @FXML
    void vehicleOnAction(ActionEvent event) {
        navigateTo("/view/vehicle-view.fxml");
    }

    public void navigateTo(String fxmlPath){
        try{
            homeAnchorPAne.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            homeAnchorPAne.getChildren().add(load);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

}
