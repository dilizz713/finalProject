package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.QueryBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.QueryDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.QueryDAOImpl;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class QueryBOImpl implements QueryBO {
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    public ArrayList<MaintenanceRecordDTO> searchMaintenanceRecordDetails(String keyword) throws SQLException {
        return null;
    }

    public ArrayList<PaymentDTO> searchPaymentDetils(String keyword) throws SQLException {
        return null;

    }

    public String getVehiclePrice(String reservationId) throws SQLException {
        return queryDAO.getVehiclePrice(reservationId);
    }

    public CustomerDTO getCustomerDetailsByReservationId(String reservationId) throws SQLException {
        return null;

    }

    public String getCustomerNameByReservationId(String reservationId) throws SQLException {
        return null;
    }
}
