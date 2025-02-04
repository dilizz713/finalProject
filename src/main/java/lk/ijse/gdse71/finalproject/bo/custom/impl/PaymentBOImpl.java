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

    public String getNextId() throws SQLException {
        return paymentDAO.getNextId();
    }

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

    public boolean deletePayments(String id) throws SQLException {
        return paymentDAO.delete(id);
    }

   /* public ArrayList<PaymentDTO> search(String keyword) throws SQLException {
        String searchQuery = """
                select p.*, c.name
                from Payment p
                join Reservation r
                on p.reservationId = r.id
                join Customer c
                on r.customerId = c.id
                where p.id Like ? or p.date Like ? or p.reservationId Like ? or p.status Like ? or c.name Like ?;
                """;

        ResultSet rst = SQLUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%","%" + keyword + "%","%" + keyword + "%","%" + keyword + "%");


        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    rst.getString(1),                    //id
                    rst.getDate(2).toLocalDate(),        //date
                    rst.getString(3),                    //status
                    rst.getString(4),                     //reservationId
                    rst.getDouble(5),                     //advance payment
                    rst.getDouble(6)                     //full payment

            );
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }*/

    public double getAdvancePayment(String reservationId) throws SQLException {
        return paymentDAO.getAdvancePayment(reservationId);
    }


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


    public PaymentDTO getPaymentDetails(String paymentId) throws SQLException {
        Payment payment =  paymentDAO.getPaymentDetails(paymentId);
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
