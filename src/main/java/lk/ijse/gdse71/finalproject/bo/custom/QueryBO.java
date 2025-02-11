package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.bo.SuperBO;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryBO extends SuperBO {
    ArrayList<MaintenanceRecordDTO> searchMaintenanceRecordDetails(String keyword) throws SQLException;

    ArrayList<PaymentDTO> searchPaymentDetils(String keyword) throws SQLException;

    String getVehiclePrice(String reservationId) throws SQLException;

    CustomerDTO getCustomerDetailsByReservationId(String reservationId) throws SQLException;

    String getCustomerNameByReservationId(String reservationId) throws SQLException;

    String getCustomerEmailByReservationId(String reservationId) throws SQLException;
}
