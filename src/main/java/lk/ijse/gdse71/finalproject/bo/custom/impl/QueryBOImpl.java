package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.QueryBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dao.custom.QueryDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.QueryDAOImpl;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.entity.Customer;
import lk.ijse.gdse71.finalproject.entity.MaintenanceRecord;
import lk.ijse.gdse71.finalproject.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryBOImpl implements QueryBO {
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ArrayList<MaintenanceRecordDTO> searchMaintenanceRecordDetails(String keyword) throws SQLException {
        return null;
    }
    @Override
    public ArrayList<PaymentDTO> searchPaymentDetils(String keyword) throws SQLException {
        return null;

    }
    @Override
    public String getVehiclePrice(String reservationId) throws SQLException {
        return queryDAO.getVehiclePrice(reservationId);
    }
    @Override
    public CustomerDTO getCustomerDetailsByReservationId(String reservationId) throws SQLException {
        return null;

    }
    @Override
    public String getCustomerNameByReservationId(String reservationId) throws SQLException {
        return null;
    }

    public String getCustomerEmailByReservationId(String reservationId) throws SQLException {
        return queryDAO.getCustomerEmailByReservationId(reservationId);
    }
}
