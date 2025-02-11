package lk.ijse.gdse71.finalproject.dao.custom.impl;

import lk.ijse.gdse71.finalproject.dao.custom.QueryDAO;
import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.entity.Customer;
import lk.ijse.gdse71.finalproject.entity.MaintenanceRecord;
import lk.ijse.gdse71.finalproject.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<MaintenanceRecord> searchMaintenanceRecordDetails(String keyword) throws SQLException {
        String searchQuery = """
                select M.* , V.model 
                from MaintenanceRecord M
                join Vehicle V 
                on M.vehicleId = V.id
                where M.id Like ? or M.vehicleId Like ?  or V.model Like ? 
                """;
        ResultSet rst = SQLUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");

        ArrayList<MaintenanceRecord> maintenanceRecordDTOS = new ArrayList<>();

        while (rst.next()) {
            MaintenanceRecord entity = new MaintenanceRecord(
                    rst.getString(1),                         //reservation id
                    rst.getDate(2).toLocalDate(),            // start date
                    rst.getDate(3).toLocalDate(),            // end date
                    rst.getString(4),                       // desc
                    rst.getString(5),                       // vehicle id
                    rst.getString(6)                       //status

            );
            maintenanceRecordDTOS.add(entity);
        }
        return maintenanceRecordDTOS;
    }
    @Override
    public ArrayList<Payment> searchPaymentDetils(String keyword) throws SQLException {
        String searchQuery = """
                select p.*, c.name 
                from Payment p
                join Reservation r
                on p.reservationId = r.id
                join Customer c
                on r.customerId = c.id
                where p.id Like ? or p.date Like ? or p.reservationId Like ? or p.status Like ? or c.name Like ?;
                """;

        ResultSet rst = SQLUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");


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
    public String getVehiclePrice(String reservationId) throws SQLException {
        ResultSet rst = SQLUtil.execute(
                """
                        select v.price
                        from Reservation r
                        join Vehicle v
                        on r.vehicleId = v.id where r.id = ?
                        """, reservationId
        );
        if (rst.next()) {
            return rst.getString("price");
        }
        return reservationId;
    }
    @Override
    public Customer getCustomerDetailsByReservationId(String reservationId) throws SQLException {
        String sql = """
                    SELECT c.id, c.name, c.email
                    FROM Customer c
                    JOIN Reservation r ON c.id = r.customerId
                    WHERE r.id = ?;
                """;
        try {
            ResultSet rst = SQLUtil.execute(sql, reservationId);
            if (rst.next()) {
                return new Customer(
                        rst.getString("id"),
                        rst.getString("name"),
                        rst.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customer details: " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }
    @Override
    public String getCustomerNameByReservationId(String reservationId) throws SQLException {
        String query = "select c.name from Customer c inner join Reservation r on c.id = r.customerId where r.id = ?";
        ResultSet resultSet = SQLUtil.execute(query, reservationId);

        return resultSet.next() ? resultSet.getString(1) : null;
    }

    public String getCustomerEmailByReservationId(String reservationId) throws SQLException {
        String query = "select c.email from Customer c inner join Reservation r on c.id = r.customerId where r.id = ?";
        ResultSet resultSet = SQLUtil.execute(query, reservationId);

        return resultSet.next() ? resultSet.getString(1) : null;
    }
}
