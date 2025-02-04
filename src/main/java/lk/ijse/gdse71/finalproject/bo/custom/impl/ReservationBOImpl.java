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

    public String getNextId() throws SQLException {
        return reservationDAO.getNextId();
    }

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

    public boolean updateReservation(ReservationDTO reservationDTO) throws SQLException {
       return reservationDAO.update(new Reservation(
               reservationDTO.getId(),
               reservationDTO.getCustomerId(),
               reservationDTO.getVehicleId(),
               reservationDTO.getStatus(),
               reservationDTO.getReservationDate()
       ));

    }


    public boolean deleteReservations(String reservationId) throws SQLException {
        return reservationDAO.delete(reservationId);
    }

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


    public ReservationDTO getReservationDetails(String reservationId) throws SQLException {
       return null;
    }

    public ArrayList<String> getAllReservationIds() throws SQLException {
        ArrayList<String> reservations =  reservationDAO.getAllReservationIds();
        ArrayList<String> reservationIds = new ArrayList<>();

        for(String reservationId : reservations) {
            reservationIds.add(reservationId);
        }
        return reservationIds;
    }

    public String getVehicleIdByReservationId(String reservationId) throws SQLException {
        return reservationDAO.getVehicleIdByReservationId(reservationId);
    }

    public String getCustomerIdByVehicleId(String vehicleId) throws SQLException {
        return reservationDAO.getCustomerIdByVehicleId(vehicleId);
    }


}
