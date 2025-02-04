package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.bo.SuperBO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    String getNextId() throws SQLException;

    ArrayList<PaymentDTO> getAllPayments() throws SQLException;

    ArrayList<PaymentDTO> searchPayments(String keyword) throws SQLException;

    boolean savePayments(PaymentDTO paymentDTO) throws SQLException;

    boolean updatePayments(PaymentDTO dto) throws SQLException;

    void updateAdvancePaymentStatus(String reservationId) throws SQLException;

    boolean deletePayments(String id) throws SQLException;

    double getAdvancePayment(String reservationId) throws SQLException;

    PaymentDTO getPaymentById(String paymentId) throws SQLException;

    PaymentDTO getPaymentDetails(String paymentId) throws SQLException;
}
