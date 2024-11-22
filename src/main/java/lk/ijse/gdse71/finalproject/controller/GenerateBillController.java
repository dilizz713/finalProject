package lk.ijse.gdse71.finalproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.model.MileageTrackingModel;
import lk.ijse.gdse71.finalproject.model.PaymentModel;
import lk.ijse.gdse71.finalproject.model.ReservationModel;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private ReservationModel reservationModel = new ReservationModel();
    private PaymentModel paymentModel = new PaymentModel();
    private MileageTrackingModel mileageTrackingModel = new MileageTrackingModel();

    private String reservationId;
    private String paymentId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (reservationId != null && paymentId != null) {
            try {
                populateBillDetails();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load bill data").show();
            }
        }
    }

    private void populateBillDetails() throws Exception {
        ReservationDTO reservationDTO = reservationModel.getReservationById(reservationId);
        PaymentDTO paymentDTO = paymentModel.getPaymentById(paymentId);
        MileageTrackingDTO mileageTrackingDTO = mileageTrackingModel.getMileageTrackingByReservationId(reservationId);


        if (reservationDTO == null || paymentDTO == null || mileageTrackingDTO == null) {
            new Alert(Alert.AlertType.ERROR, "Data not found for this reservation/payment.").show();
            return;
        }


        lblReservationId.setText(reservationDTO.getId());
        lblPaymentId.setText(paymentDTO.getId());


        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));



        double estimatedMileage = 0, actualMilege = 0, estimatedMileageCost = 0, extrachargesPerKm = 0, totalExtraCharges = 0 , damageCost = 0, advance = 0, totalBeforeAdvance = 0, fullamount = 0;


        estimatedMileage = mileageTrackingDTO.getEstimatedMileage();
        actualMilege = mileageTrackingDTO.getActualMileage();
        estimatedMileageCost = mileageTrackingDTO.getEstimatedMileageCost();
        extrachargesPerKm = mileageTrackingDTO.getExtraChargePerKm();
        totalExtraCharges = mileageTrackingDTO.getTotalExtraCharges();

        String vehicleId = reservationModel.getVehicleIdByReservationId(reservationId);
        damageCost = reservationModel.getRepairCostByVehicleId(vehicleId);
        advance = paymentModel.getAdvancePayment(reservationId);


       totalBeforeAdvance = estimatedMileageCost + totalExtraCharges + damageCost;
       fullamount = totalBeforeAdvance - advance;


        lblEstimatedMileage.setText(String.valueOf(estimatedMileage));
        lblActualMileage.setText(String.valueOf(actualMilege));
        lblEstimatedMileageCost.setText(String.valueOf(estimatedMileageCost));
        lblExtraChargesPerKm.setText(String.valueOf(extrachargesPerKm));
        lblTotalExtraCharges.setText(String.valueOf(totalExtraCharges));
        lblDamageCost.setText(String.valueOf(damageCost));
        lbladvancePayment.setText(String.valueOf(advance));
        lblTotal.setText(String.valueOf(totalBeforeAdvance));
        lblFyllPayment.setText(String.valueOf(fullamount));




    }

    // Setter methods to pass reservationId and paymentId from another controller
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
