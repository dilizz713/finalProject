package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.PaymentDTO;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<PaymentDTO> {
    void updateAdvancePaymentStatus(String reservationId) throws SQLException;
    boolean updatePayment(PaymentDTO paymentDTO) throws SQLException;
    double getAdvancePayment(String reservationId) throws SQLException;
    PaymentDTO getPaymentById(String paymentId) throws SQLException;
    PaymentDTO getPaymentDetails(String paymentId) throws SQLException;
}
