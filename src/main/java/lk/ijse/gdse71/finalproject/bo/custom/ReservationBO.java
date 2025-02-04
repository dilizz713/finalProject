package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.bo.SuperBO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationBO extends SuperBO {
    String getNextId() throws SQLException;

    ArrayList<ReservationDTO> getAllReservations() throws SQLException;

   //boolean saveReservations(ReservationDTO reservationDTO) throws SQLException;

    boolean updateReservation(ReservationDTO reservationDTO) throws SQLException;

    boolean deleteReservations(String reservationId) throws SQLException;

    ArrayList<ReservationDTO> searchReservations(String keyword) throws SQLException;

    ReservationDTO getReservationById(String reservationId) throws SQLException;

    ReservationDTO getReservationDetails(String reservationId) throws SQLException;

    ArrayList<String> getAllReservationIds() throws SQLException;

    String getVehicleIdByReservationId(String reservationId) throws SQLException;

    String getCustomerIdByVehicleId(String vehicleId) throws SQLException;
}
