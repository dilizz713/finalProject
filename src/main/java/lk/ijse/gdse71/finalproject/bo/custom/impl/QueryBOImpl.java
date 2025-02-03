package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.QueryBO;
import lk.ijse.gdse71.finalproject.dao.custom.QueryDAO;
import lk.ijse.gdse71.finalproject.dao.custom.SQLUtil;
import lk.ijse.gdse71.finalproject.dao.custom.impl.QueryDAOImpl;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryBOImpl implements QueryBO {
    QueryDAO queryDAO = new QueryDAOImpl();
    public ArrayList<MaintenanceRecordDTO> searchMaintenanceRecordDetails(String keyword) throws SQLException {

    }

    public ArrayList<PaymentDTO> searchPaymentDetils(String keyword) throws SQLException {

    }

    public String getVehiclePrice(String reservationId) throws SQLException {
       return  queryDAO.getVehiclePrice(reservationId);
    }

    public CustomerDTO getCustomerDetailsByReservationId(String reservationId) throws SQLException {


    }

    public String getCustomerNameByReservationId(String reservationId) throws SQLException {

    }
}
