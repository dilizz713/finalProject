package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.bo.SuperBO;
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MileageTrackingBO extends SuperBO {
    String getNextId() throws SQLException;

    ArrayList<MileageTrackingDTO> getAllMileageTracking() throws SQLException;

    boolean saveMileageTracking(MileageTrackingDTO mileageTrackingDTO) throws SQLException;

    boolean updateMileageTracking(MileageTrackingDTO mileageTrackingDTO) throws SQLException;

    boolean deleteMileageTracking(String trackingId) throws SQLException;

    MileageTrackingDTO getMileageTrackingByReservationId(String reservationId) throws SQLException;

    ArrayList<MileageTrackingDTO> searchMileageTracking(String keyword) throws SQLException;

    double getEstimatedMileageCost(String reservationId) throws SQLException;

    double getTotalExtraCharges(String reservationId) throws SQLException;

    double getEndMileageForReservation(String reservationId) throws SQLException;

    MileageTrackingDTO getMileageDetails(String reservationId) throws SQLException;
}
