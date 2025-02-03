package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.MileageTrackingBO;
import lk.ijse.gdse71.finalproject.dao.custom.MileageTrackingDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.MileageTrackingDAOImpl;
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class MileageTrackingBOImpl implements MileageTrackingBO {

    MileageTrackingDAO mileageTrackingDAO = new MileageTrackingDAOImpl();

    public String getNextId() throws SQLException {
       return mileageTrackingDAO.getNextId();
    }

    public ArrayList<MileageTrackingDTO> getAllMileageTracking() throws SQLException {
        return mileageTrackingDAO.getAll();
    }

   /* public ArrayList<String> getAllReservationIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select id from Reservation");
        ArrayList<String> reservationId = new ArrayList<>();
        while (rst.next()) {
            reservationId.add(rst.getString(1));
        }
        return reservationId;
    }*/

    public boolean saveMileageTracking(MileageTrackingDTO mileageTrackingDTO) throws SQLException {
        return mileageTrackingDAO.save(mileageTrackingDTO);
    }



    public boolean updateMileageTracking(MileageTrackingDTO mileageTrackingDTO) throws SQLException {
        return mileageTrackingDAO.update(mileageTrackingDTO);
    }

    public boolean deleteMileageTracking(String trackingId) throws SQLException {
       return mileageTrackingDAO.delete(trackingId);
    }

    public MileageTrackingDTO getMileageTrackingByReservationId(String reservationId) throws SQLException {
        return null;
    }

    public ArrayList<MileageTrackingDTO> searchMileageTracking(String keyword) throws SQLException {
        return mileageTrackingDAO.search(keyword);
    }

    public double getEstimatedMileageCost(String reservationId) throws SQLException {
        return  mileageTrackingDAO.getEstimatedMileageCost(reservationId);
    }

    public double getTotalExtraCharges(String reservationId) throws SQLException {
        return mileageTrackingDAO.getTotalExtraCharges(reservationId);
    }

    public double getEndMileageForReservation(String reservationId) throws SQLException {
        return mileageTrackingDAO.getEndMileageForReservation(reservationId);
    }
    public MileageTrackingDTO getMileageDetails(String reservationId) throws SQLException {
        return mileageTrackingDAO.getMileageDetails(reservationId);
    }
}
