package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.ReservationBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dao.custom.ReservationDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.ReservationDAOImpl;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.entity.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {
    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    @Override
    public String getNextId() throws SQLException {
        return reservationDAO.getNextId();
    }
    @Override
    public ArrayList<ReservationDTO> getAllReservations() throws SQLException {
        ArrayList<Reservation> reservations =  reservationDAO.getAll();
        ArrayList<ReservationDTO> reservationDTOs = new ArrayList<>();

       for(Reservation reservation : reservations) {
           reservationDTOs.add(new ReservationDTO(
                   reservation.getId(),
                   reservation.getCustomerId(),
                   reservation.getVehicleId(),
                   reservation.getStatus(),
                   reservation.getReservationDate()
           ));
       }
       return reservationDTOs;
    }
    @Override
    public boolean updateReservation(ReservationDTO reservationDTO) throws SQLException {
       return reservationDAO.update(new Reservation(
               reservationDTO.getId(),
               reservationDTO.getCustomerId(),
               reservationDTO.getVehicleId(),
               reservationDTO.getStatus(),
               reservationDTO.getReservationDate()
       ));

    }

    @Override
    public boolean deleteReservations(String reservationId) throws SQLException {
        return reservationDAO.delete(reservationId);
    }
    @Override
    public ArrayList<ReservationDTO> searchReservations(String keyword) throws SQLException {
        ArrayList<Reservation> reservations =  reservationDAO.search(keyword);
        ArrayList<ReservationDTO> reservationDTOs = new ArrayList<>();

        for(Reservation reservation : reservations) {
            reservationDTOs.add(new ReservationDTO(
                    reservation.getId(),
                    reservation.getCustomerId(),
                    reservation.getVehicleId(),
                    reservation.getStatus(),
                    reservation.getReservationDate()
            ));
        }
        return reservationDTOs;

    }
    @Override
    public ReservationDTO getReservationById(String reservationId) throws SQLException {
        Reservation reservation =  reservationDAO.getReservationById(reservationId);
        return new ReservationDTO(
                reservation.getId(),
                reservation.getCustomerId(),
                reservation.getVehicleId(),
                reservation.getStatus(),
                reservation.getReservationDate()
        );
    }


    @Override
    public ArrayList<String> getAllReservationIds() throws SQLException {
        ArrayList<String> reservations =  reservationDAO.getAllReservationIds();
        ArrayList<String> reservationIds = new ArrayList<>();

        for(String reservationId : reservations) {
            reservationIds.add(reservationId);
        }
        return reservationIds;
    }
    @Override
    public String getVehicleIdByReservationId(String reservationId) throws SQLException {
        return reservationDAO.getVehicleIdByReservationId(reservationId);
    }
    @Override
    public String getCustomerIdByVehicleId(String vehicleId) throws SQLException {
        return reservationDAO.getCustomerIdByVehicleId(vehicleId);
    }


}
