package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.MileageTrackingBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.MileageTrackingDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.MileageTrackingDAOImpl;
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.entity.MileageTracking;

import java.sql.SQLException;
import java.util.ArrayList;

public class MileageTrackingBOImpl implements MileageTrackingBO {

    MileageTrackingDAO mileageTrackingDAO = (MileageTrackingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MILEAGETRACKING);

    public String getNextId() throws SQLException {
        return mileageTrackingDAO.getNextId();
    }

    public ArrayList<MileageTrackingDTO> getAllMileageTracking() throws SQLException {
        ArrayList<MileageTracking> mileageTrackings =  mileageTrackingDAO.getAll();
        ArrayList<MileageTrackingDTO> mileageTrackingDTOS = new ArrayList<>();

        for(MileageTracking mileageTracking : mileageTrackings){
            mileageTrackingDTOS.add(new MileageTrackingDTO(
                    mileageTracking.getId(),
                    mileageTracking.getEstimatedMileage(),
                    mileageTracking.getActualMileage(),
                    mileageTracking.getExtraChargePerKm(),
                    mileageTracking.getTotalExtraCharges(),
                    mileageTracking.getReservationId(),
                    mileageTracking.getStartDateMileage(),
                    mileageTracking.getEndDateMileage(),
                    mileageTracking.getEstimatedMileageCost(),
                    mileageTracking.getStartDate(),
                    mileageTracking.getEndDate()

            ));
        }
        return mileageTrackingDTOS;
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
        return mileageTrackingDAO.save(new MileageTracking(
                mileageTrackingDTO.getId(),
                mileageTrackingDTO.getEstimatedMileage(),
                mileageTrackingDTO.getActualMileage(),
                mileageTrackingDTO.getExtraChargePerKm(),
                mileageTrackingDTO.getTotalExtraCharges(),
                mileageTrackingDTO.getReservationId(),
                mileageTrackingDTO.getStartDateMileage(),
                mileageTrackingDTO.getEndDateMileage(),
                mileageTrackingDTO.getEstimatedMileageCost(),
                mileageTrackingDTO.getStartDate(),
                mileageTrackingDTO.getEndDate()
        ));
    }


    public boolean updateMileageTracking(MileageTrackingDTO mileageTrackingDTO) throws SQLException {
        return mileageTrackingDAO.update(new MileageTracking(
                mileageTrackingDTO.getId(),
                mileageTrackingDTO.getEstimatedMileage(),
                mileageTrackingDTO.getActualMileage(),
                mileageTrackingDTO.getExtraChargePerKm(),
                mileageTrackingDTO.getTotalExtraCharges(),
                mileageTrackingDTO.getReservationId(),
                mileageTrackingDTO.getStartDateMileage(),
                mileageTrackingDTO.getEndDateMileage(),
                mileageTrackingDTO.getEstimatedMileageCost(),
                mileageTrackingDTO.getStartDate(),
                mileageTrackingDTO.getEndDate()
        ));
    }

    public boolean deleteMileageTracking(String trackingId) throws SQLException {
        return mileageTrackingDAO.delete(trackingId);
    }

    public MileageTrackingDTO getMileageTrackingByReservationId(String reservationId) throws SQLException {
        return null;
    }

    public ArrayList<MileageTrackingDTO> searchMileageTracking(String keyword) throws SQLException {
        ArrayList<MileageTracking> mileageTrackings =  mileageTrackingDAO.search(keyword);
        ArrayList<MileageTrackingDTO> mileageTrackingDTOS = new ArrayList<>();

        for(MileageTracking mileageTracking : mileageTrackings){
            mileageTrackingDTOS.add(new MileageTrackingDTO(
                    mileageTracking.getId(),
                    mileageTracking.getEstimatedMileage(),
                    mileageTracking.getActualMileage(),
                    mileageTracking.getExtraChargePerKm(),
                    mileageTracking.getTotalExtraCharges(),
                    mileageTracking.getReservationId(),
                    mileageTracking.getStartDateMileage(),
                    mileageTracking.getEndDateMileage(),
                    mileageTracking.getEstimatedMileageCost(),
                    mileageTracking.getStartDate(),
                    mileageTracking.getEndDate()

            ));
        }
        return mileageTrackingDTOS;
    }

    public double getEstimatedMileageCost(String reservationId) throws SQLException {
        return mileageTrackingDAO.getEstimatedMileageCost(reservationId);
    }

    public double getTotalExtraCharges(String reservationId) throws SQLException {
        return mileageTrackingDAO.getTotalExtraCharges(reservationId);
    }

    public double getEndMileageForReservation(String reservationId) throws SQLException {
        return mileageTrackingDAO.getEndMileageForReservation(reservationId);
    }

    public MileageTrackingDTO getMileageDetails(String reservationId) throws SQLException {
         MileageTracking mileageTracking = mileageTrackingDAO.getMileageDetails(reservationId);
         return new MileageTrackingDTO(
                 mileageTracking.getEstimatedMileage(),
                 mileageTracking.getActualMileage(),
                 mileageTracking.getExtraChargePerKm(),
                 mileageTracking.getTotalExtraCharges(),
                 mileageTracking.getEstimatedMileageCost()
         );
    }
}
