package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.SuperDAO;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<MaintenanceRecordDTO> searchMaintenanceRecordDetails(String keyword) throws SQLException;
    ArrayList<PaymentDTO> searchPaymentDetils(String keyword) throws SQLException;
    String getVehiclePrice(String reservationId) throws SQLException;
    CustomerDTO getCustomerDetailsByReservationId(String reservationId) throws SQLException;
    String getCustomerNameByReservationId(String reservationId) throws SQLException;

}
