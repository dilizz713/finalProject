package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.SuperDAO;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.entity.Customer;
import lk.ijse.gdse71.finalproject.entity.MaintenanceRecord;
import lk.ijse.gdse71.finalproject.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<MaintenanceRecord> searchMaintenanceRecordDetails(String keyword) throws SQLException;

    ArrayList<Payment> searchPaymentDetils(String keyword) throws SQLException;

    String getVehiclePrice(String reservationId) throws SQLException;

    Customer getCustomerDetailsByReservationId(String reservationId) throws SQLException;

    String getCustomerNameByReservationId(String reservationId) throws SQLException;

}
