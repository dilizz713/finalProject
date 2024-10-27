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

    }

    @FXML
    void invoiceOnAction(ActionEvent event) {

    }

    @FXML
    void maintenanceOnAction(ActionEvent event) {

    }

    @FXML
    void mileageTrackingOnAction(ActionEvent event) {

    }

    @FXML
    void paymentOnAction(ActionEvent event) {

    }

    @FXML
    void reservationOnAction(ActionEvent event) {

    }

    @FXML
    void vehicleOnAction(ActionEvent event) {

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
