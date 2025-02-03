package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.ReservationBO;
import lk.ijse.gdse71.finalproject.dao.custom.ReservationDAO;
import lk.ijse.gdse71.finalproject.dao.custom.SQLUtil;
import lk.ijse.gdse71.finalproject.dao.custom.impl.ReservationDAOImpl;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {
    ReservationDAO reservationDAO = new ReservationDAOImpl();

    public String getNextId() throws SQLException {
        return reservationDAO.getNextId();
    }
    public ArrayList<ReservationDTO> getAllReservations() throws SQLException {
        return reservationDAO.getAll();
    }
    public boolean saveReservations(ReservationDTO reservationDTO) throws SQLException {


    }


    public boolean updateReservations(ReservationDTO reservationDTO) throws SQLException {
       return reservationDAO.update(reservationDTO);
    }

    public boolean deleteReservations(String reservationId) throws SQLException {
       return reservationDAO.delete(reservationId);
    }

    public ArrayList<ReservationDTO> searchReservations(String keyword) throws SQLException {
       return reservationDAO.search(keyword);
    }
    public ReservationDTO getReservationById(String reservationId) throws SQLException {
       return reservationDAO.getReservationById(reservationId);;
    }


    public ReservationDTO getReservationDetails(String reservationId) throws SQLException {

    }

    public ArrayList<String> getAllReservationIds() throws SQLException {
        return reservationDAO.getAllReservationIds();
    }

    public String getVehicleIdByReservationId(String reservationId) throws SQLException {
        return reservationDAO.getVehicleIdByReservationId(reservationId);
    }

    public String getCustomerIdByVehicleId(String vehicleId) throws SQLException {

    }
}
