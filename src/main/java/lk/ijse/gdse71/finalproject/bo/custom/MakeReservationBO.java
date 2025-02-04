package lk.ijse.gdse71.finalproject.bo.custom;

import javafx.scene.control.Button;
import lk.ijse.gdse71.finalproject.bo.SuperBO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;

import java.sql.SQLException;

public interface MakeReservationBO extends SuperBO {
    void saveReservationAndPayment(ReservationDTO reservationDTO, PaymentDTO paymentDTO, Button updatePAymentButton) throws SQLException;

    void updatePayment(PaymentDTO paymentDTO, String status, String reservationId, Button updatePAymentButton) throws SQLException;

    void showBillUI(String reservationId, String paymentId, Button updatePAymentButton);
}
