package lk.ijse.gdse71.finalproject.dao.custom.impl;

import lk.ijse.gdse71.finalproject.dao.custom.ReservationDAO;
import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.entity.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select id from Reservation order by id desc limit 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i + 1;
            return String.format("R%03d", newId);
        }
        return "R001";
    }
    @Override
    public ArrayList<Reservation> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Reservation");

        ArrayList<Reservation> entity = new ArrayList<>();

        while (rst.next()) {
            LocalDate reservationDate = rst.getDate("reservationDate") != null
                    ? rst.getDate("reservationDate").toLocalDate()
                    : null;


            entity.add(new Reservation(
                    rst.getString(1),                       //reservation id
                    rst.getString(2),                       //customer id
                    rst.getString(3),                       //vehicle id
                    rst.getString(4),                       //status
                    reservationDate
            ));

        }

        return entity;
    }


    @Override
    public boolean save(Reservation entity) throws SQLException {
        return SQLUtil.execute(
                "insert into Reservation values (?,?,?,?,?)",
                entity.getId(),
                entity.getCustomerId(),
                entity.getVehicleId(),
                entity.getStatus(),
                entity.getReservationDate()
        );

    }

    @Override
    public boolean update(Reservation entity) throws SQLException {
        return SQLUtil.execute(
                "update  Reservation set customerId=?, vehicleId=?,  status=?, reservationDate=? where id=?",
                entity.getCustomerId(),
                entity.getVehicleId(),
                entity.getStatus(),
                entity.getReservationDate(),
                entity.getId()
        );
    }
    @Override
    public boolean delete(String reservationId) throws SQLException {
        return SQLUtil.execute("delete from Reservation where id=?", reservationId);
    }
    @Override
    public ArrayList<Reservation> search(String keyword) throws SQLException {
        String searchQuery = "select * from Reservation where id like ? or vehicleId like ? or customerId like ? or reservationDate like ? or status like ?";

        ResultSet rst = SQLUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");


        ArrayList<Reservation> reservationDTOS = new ArrayList<>();

        while (rst.next()) {
            Reservation entity = new Reservation(
                    rst.getString(1),                       //reservation id
                    rst.getString(2),                       //customer id
                    rst.getString(3),                       //vehicle id
                    rst.getString(4),                       //status
                    rst.getDate(5).toLocalDate()             //reservation date

            );
            reservationDTOS.add(entity);
        }
        return reservationDTOS;
    }


    @Override
    public Reservation getReservationById(String reservationId) throws SQLException {
        String query = "select * from Reservation where id=?";
        ResultSet rst = SQLUtil.execute(query, reservationId);

        if (rst.next()) {
            String id = rst.getString("id");
            String customerId = rst.getString("customerId");
            String vehicleId = rst.getString("vehicleId");
            String status = rst.getString("status");
            LocalDate reservationDate = rst.getDate("reservationDate").toLocalDate();

            return new Reservation(id, customerId, vehicleId, status, reservationDate);

        }
        return null;
    }


    @Override
    public ArrayList<String> getAllReservationIds() throws SQLException {
        String query = "select id from Reservation";
        ResultSet rst = SQLUtil.execute(query);

        ArrayList<String> reservationID = new ArrayList<>();
        while (rst.next()) {
            reservationID.add(rst.getString(1));
        }
        return reservationID;
    }


    @Override
    public String getVehicleIdByReservationId(String reservationId) throws SQLException {
        String query = "select vehicleId from Reservation where id = ?";

        ResultSet rst = SQLUtil.execute(query, reservationId);
        if (rst.next()) {
            return rst.getString("vehicleId");
        }

        return null;
    }
    @Override
    public String getCustomerIdByVehicleId(String vehicleId) throws SQLException {
        String sql = "select customerId from Reservation where vehicleId = ?";
        ResultSet resultSet = SQLUtil.execute(sql, vehicleId);

        if (resultSet.next()) {
            return resultSet.getString("customerId");
        }
        return null;
    }
}
