package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MileageTrackingDAO extends CrudDAO<MileageTrackingDTO> {
   //ArrayList<String> getAllReservationIds() throws SQLException;
    double getEstimatedMileageCost(String reservationId) throws SQLException;
    double getTotalExtraCharges(String reservationId) throws SQLException;
    double getEndMileageForReservation(String reservationId) throws SQLException;
    MileageTrackingDTO getMileageDetails(String reservationId) throws SQLException;
}
