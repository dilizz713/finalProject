package lk.ijse.gdse71.finalproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lk.ijse.gdse71.finalproject.bo.BOFactory;
import lk.ijse.gdse71.finalproject.bo.custom.VehicleDamageBO;
import lk.ijse.gdse71.finalproject.bo.custom.impl.VehicleDamageBOImpl;
import lk.ijse.gdse71.finalproject.dto.*;
import lk.ijse.gdse71.finalproject.entity.MileageTracking;
import lk.ijse.gdse71.finalproject.entity.Payment;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GenerateBillController implements Initializable {

    @FXML
    private Label lblActualMileage;

    @FXML
    private Label lblDamageCost;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEstimatedMileage;

    @FXML
    private Label lblEstimatedMileageCost;

    @FXML
    private Label lblExtraChargesPerKm;

    @FXML
    private Label lblFyllPayment;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblReservationId;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTotalExtraCharges;

    @FXML
    private Label lbladvancePayment;

    @FXML
    private Button btnEmail;


    private VehicleDamageBO vehicleDamageBO = (VehicleDamageBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.VEHICLEDAMAGE);

    private String reservationId;
    private String paymentId;
    private String vehicleId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setBillDetails(String reservationId, String paymentId, Payment payment, MileageTracking mileage, String vehicleId) {
        this.reservationId = reservationId;
        this.paymentId = paymentId;
        this.vehicleId = vehicleId;

        lblReservationId.setText(reservationId);
        lblPaymentId.setText(paymentId);
        lblDate.setText(LocalDate.now().toString());

        lblActualMileage.setText(String.format("%.2f km", mileage.getActualMileage()));
        lblEstimatedMileage.setText(String.format("%.2f km", mileage.getEstimatedMileage()));
        lblEstimatedMileageCost.setText(String.format("LKR%.2f", mileage.getEstimatedMileageCost()));
        lblExtraChargesPerKm.setText(String.format("LKR%.2f/km", mileage.getExtraChargePerKm()));
        lblTotalExtraCharges.setText(String.format("LKR%.2f", mileage.getTotalExtraCharges()));

        lbladvancePayment.setText(String.format("LKR%.2f", payment.getAdvancePayment()));
        lblFyllPayment.setText(String.format("LKR%.2f", payment.getFullPayment()));

        try {

            double repairCost = vehicleDamageBO.getRepairCostByVehicleId(vehicleId);
            lblDamageCost.setText(String.format("LKR%.2f", repairCost));


            double total = mileage.getEstimatedMileageCost() + mileage.getTotalExtraCharges() + repairCost;
            lbladvancePayment.setText(String.format("LKR%.2f", payment.getAdvancePayment()));
            lblFyllPayment.setText(String.format("LKR%.2f", payment.getFullPayment()));
            lblTotal.setText(String.format("LKR%.2f", total));

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to retrieve repair cost!").show();
        }
    }


}
