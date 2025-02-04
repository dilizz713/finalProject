package lk.ijse.gdse71.finalproject.dao.custom.impl;

import lk.ijse.gdse71.finalproject.dao.custom.PaymentDAO;
import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    public String getNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select id from Payment order by id desc limit 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i + 1;
            return String.format("P%03d", newId);
        }
        return "P001";
    }

    public ArrayList<Payment> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Payment");

        ArrayList<Payment> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            Payment entity = new Payment(
                    rst.getString(1),                    //id
                    rst.getDate(2).toLocalDate(),        //date
                    rst.getString(3),                    //status
                    rst.getString(4),                     //reservationId
                    rst.getDouble(5),                     //advance payment
                    rst.getDouble(6)                     //full payment

            );
            paymentDTOS.add(entity);
        }
        return paymentDTOS;
    }

    @Override
    public ArrayList<Payment> search(String keyword) throws SQLException {
        return null;
    }

    public boolean save(Payment entity) throws SQLException {
        return SQLUtil.execute(
                "insert into Payment values (?,?,?,?,?,?)",
                entity.getId(),
                entity.getDate(),
                entity.getStatus(),
                entity.getReservationId(),
                entity.getAdvancePayment(),
                entity.getFullPayment()

        );

    }

    public void updateAdvancePaymentStatus(String reservationId) throws SQLException {
        String query = "update Payment set status = 'Done' where reservationId = ? and type = 'Advance Payment'";
        SQLUtil.execute(query, reservationId);
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("delete from Payment where id=?", id);
    }

    public boolean update(Payment entity) throws SQLException {
        return SQLUtil.execute(
                "update Payment set  date=?,  status=?, reservationId=?,advancePayment=? , fullPayment=?  where id=?",
                entity.getDate(),
                entity.getStatus(),
                entity.getReservationId(),
                entity.getAdvancePayment(),
                entity.getFullPayment(),
                entity.getId()

        );
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
        String query = "select sum(advancePayment) as totalAdvancePayment from Payment where reservationId = ?";
        ResultSet resultSet = SQLUtil.execute(query, reservationId);

        if (resultSet.next()) {
            return resultSet.getDouble("totalAdvancePayment");
        }
        return 0.0;
    }


    public Payment getPaymentById(String paymentId) throws SQLException {
        String query = "select * from Payment where id=?";
        ResultSet rst = SQLUtil.execute(query, paymentId);

        if (rst.next()) {
            String id = rst.getString("id");
            LocalDate date = rst.getDate("date").toLocalDate();
            String status = rst.getString("status");
            String reservationId = rst.getString("reservationId");
            double advancePayment = rst.getDouble("advancePayment");
            double fullPayment = rst.getDouble("fullPayment");


            return new Payment(id, date, status, reservationId, advancePayment, fullPayment);

        }
        return null;
    }


    public Payment getPaymentDetails(String paymentId) throws SQLException {
        String query = "SELECT * FROM Payment WHERE id = ?";
        ResultSet rst = SQLUtil.execute(query, paymentId);

        if (rst.next()) {
            return new Payment(
                    rst.getString("id"),
                    rst.getDate("date").toLocalDate(),
                    rst.getString("status"),
                    rst.getString("reservationId"),
                    rst.getDouble("advancePayment"),
                    rst.getDouble("fullPayment")
            );
        }
        return null;
    }
}
