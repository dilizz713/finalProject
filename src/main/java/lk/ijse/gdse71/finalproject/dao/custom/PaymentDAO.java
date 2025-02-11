package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.entity.Payment;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<Payment> {
    void updateAdvancePaymentStatus(String reservationId) throws SQLException;

    double getAdvancePayment(String reservationId) throws SQLException;

    Payment getPaymentById(String paymentId) throws SQLException;

    Payment getPaymentDetails(String paymentId) throws SQLException;
}
