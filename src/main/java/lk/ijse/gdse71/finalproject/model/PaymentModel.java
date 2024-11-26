package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select id from Payment order by id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i+1;
            return String.format("P%03d",newId);
        }
        return "P001";
    }

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Payment");

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
    }

    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Payment values (?,?,?,?,?,?)",
                paymentDTO.getId(),
                paymentDTO.getDate(),
                paymentDTO.getStatus(),
                paymentDTO.getReservationId(),
                paymentDTO.getAdvancePayment(),
                paymentDTO.getFullPayment()

        );

    }


    public void updateAdvancePaymentStatus(String reservationId) throws SQLException {
        String query = "update Payment set status = 'Done' where reservationId = ? and type = 'Advance Payment'";
        CrudUtil.execute(query, reservationId);
    }

    public boolean deletePayment(String id) throws SQLException {
        return CrudUtil.execute("delete from Payment where id=?", id);
    }

    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException {
        return CrudUtil.execute(
                "update Payment set  date=?,  status=?, reservationId=?,advancePayment=? , fullPayment=?  where id=?",
                paymentDTO.getDate(),
                paymentDTO.getStatus(),
                paymentDTO.getReservationId(),
                paymentDTO.getAdvancePayment(),
                paymentDTO.getFullPayment(),
                paymentDTO.getId()

        );
    }

    public ArrayList<PaymentDTO> getPaymentRecordsBySearch(String keyword) throws SQLException {
        String searchQuery = """
                select p.*, c.name 
                from Payment p
                join Reservation r
                on p.reservationId = r.id
                join Customer c
                on r.customerId = c.id
                where p.id Like ? or p.date Like ? or p.reservationId Like ? or p.status Like ? or c.name Like ?;
                """;

        ResultSet rst = CrudUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%","%" + keyword + "%","%" + keyword + "%","%" + keyword + "%");


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
    }

    public double getAdvancePayment(String reservationId) throws SQLException {
        String query = "select sum(advancePayment) as totalAdvancePayment from Payment where reservationId = ?";
        ResultSet resultSet = CrudUtil.execute(query, reservationId);

        if (resultSet.next()) {
            return resultSet.getDouble("totalAdvancePayment");
        }
        return 0.0;
    }


    public PaymentDTO getPaymentById(String paymentId) throws SQLException {
        String query = "select * from Payment where id=?";
        ResultSet rst = CrudUtil.execute(query,paymentId);

        if(rst.next()){
            String id = rst.getString("id");
            LocalDate date = rst.getDate("date").toLocalDate();
            String status = rst.getString("status");
            String reservationId = rst.getString("reservationId");
            double advancePayment = rst.getDouble("advancePayment");
            double fullPayment = rst.getDouble("fullPayment");


            return new PaymentDTO(id,date,status,reservationId,advancePayment,fullPayment);

        }
        return null;
    }


    public PaymentDTO getPaymentDetails(String paymentId) throws SQLException {
        String query = "SELECT * FROM Payment WHERE id = ?";
        ResultSet rst = CrudUtil.execute(query, paymentId);

        if (rst.next()) {
            return new PaymentDTO(
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
