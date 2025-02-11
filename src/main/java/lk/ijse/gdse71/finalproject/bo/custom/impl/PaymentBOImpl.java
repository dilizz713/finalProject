package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.PaymentBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.PaymentDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public String getNextId() throws SQLException {
        return paymentDAO.getNextId();
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ArrayList<Payment> payments =  paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOs = new ArrayList<>();

        for(Payment payment : payments){
           paymentDTOs.add(new PaymentDTO(
                   payment.getId(),
                   payment.getDate(),
                   payment.getStatus(),
                   payment.getReservationId(),
                   payment.getAdvancePayment(),
                   payment.getFullPayment()
           ));
        }
        return paymentDTOs;
    }
    @Override
    public boolean deletePayments(String id) throws SQLException {
        return paymentDAO.delete(id);
    }


    @Override
    public double getAdvancePayment(String reservationId) throws SQLException {
        return paymentDAO.getAdvancePayment(reservationId);
    }

    @Override
    public PaymentDTO getPaymentById(String paymentId) throws SQLException {
        Payment payment =  paymentDAO.getPaymentById(paymentId);
        return new PaymentDTO(
                payment.getId(),
                payment.getDate(),
                payment.getStatus(),
                payment.getReservationId(),
                payment.getAdvancePayment(),
                payment.getFullPayment()
        );
    }



}
