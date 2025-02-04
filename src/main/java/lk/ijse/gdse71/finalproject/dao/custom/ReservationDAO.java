package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.entity.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationDAO extends CrudDAO<Reservation> {
    // ArrayList<String> getAllCustomerIds() throws SQLException;
    // ArrayList<String> getAllVehicleIds() throws SQLException;
    // String getCustomerNameById(String customerId) throws SQLException;
    // String getVehiclePriceById(String vehicleID) throws SQLException;
    // String getVehicleNameById(String vehicleId) throws SQLException;
    // ArrayList<CustomerDTO> getCustomerDTOsForReservation() throws SQLException;
    // String getNumberPlateById(String vehicleId) throws SQLException;
    // String getAllVehicleDetails(String vehicleId) throws SQLException;
    Reservation getReservationById(String reservationId) throws SQLException;

    //  ReservationDTO getReservationDetails(String reservationId) throws SQLException;
    // String getVehiclePrice(String reservationId) throws SQLException;
    ArrayList<String> getAllReservationIds() throws SQLException;

    // String getCustomerNameByReservationId(String reservationId) throws SQLException;
    String getVehicleIdByReservationId(String reservationId) throws SQLException;

    // double getEstimatedMileageCost(String reservationId) throws SQLException;
    //  double getTotalExtraCharges(String reservationId) throws SQLException;
    //  double getRepairCostByVehicleId(String vehicleId) throws SQLException;
    //  double getEndMileageForReservation(String reservationId) throws SQLException;
    // String getPaymentIdByReservation(String reservationId) throws SQLException;
    //  MileageTrackingDTO getMileageDetails(String reservationId) throws SQLException;
    // CustomerDTO getCustomerDetailsByReservationId(String reservationId) throws SQLException;
    String getCustomerIdByVehicleId(String vehicleId) throws SQLException;

}
