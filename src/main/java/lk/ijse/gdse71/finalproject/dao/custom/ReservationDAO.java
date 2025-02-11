package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.entity.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationDAO extends CrudDAO<Reservation> {
    Reservation getReservationById(String reservationId) throws SQLException;
    ArrayList<String> getAllReservationIds() throws SQLException;
    String getVehicleIdByReservationId(String reservationId) throws SQLException;
    String getCustomerIdByVehicleId(String vehicleId) throws SQLException;

}
