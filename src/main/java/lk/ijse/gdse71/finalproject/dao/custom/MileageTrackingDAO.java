package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.entity.MileageTracking;

import java.sql.SQLException;

public interface MileageTrackingDAO extends CrudDAO<MileageTracking> {
    //ArrayList<String> getAllReservationIds() throws SQLException;
    double getEstimatedMileageCost(String reservationId) throws SQLException;

    double getTotalExtraCharges(String reservationId) throws SQLException;

    double getEndMileageForReservation(String reservationId) throws SQLException;

    MileageTracking getMileageDetails(String reservationId) throws SQLException;
}
