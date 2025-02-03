package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.PaymentBO;
import lk.ijse.gdse71.finalproject.dao.custom.PaymentDAO;
import lk.ijse.gdse71.finalproject.dao.custom.SQLUtil;
import lk.ijse.gdse71.finalproject.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = new PaymentDAOImpl();

    public String getNextId() throws SQLException {
        return paymentDAO.getNextId();
    }

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        return paymentDAO.getAll();
    }

    @Override
    public ArrayList<PaymentDTO> searchPayments(String keyword) throws SQLException {
        return paymentDAO.search(keyword);
    }

    public boolean savePayments(PaymentDTO paymentDTO) throws SQLException {
        return false;

    }



    public void updateAdvancePaymentStatus(String reservationId) throws SQLException {

    }

    public boolean deletePayments(String id) throws SQLException {
        return paymentDAO.delete(id);
    }

    public boolean updatePayments(PaymentDTO paymentDTO) throws SQLException {
        return false;
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
       return paymentDAO.getPaymentById(paymentId);
    }


    public PaymentDTO getPaymentDetails(String paymentId) throws SQLException {
        return paymentDAO.getPaymentDetails(paymentId);
    }
}
