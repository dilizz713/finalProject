package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<CustomerDTO> {
    ArrayList<String> getAllCustomerNames() throws SQLException;
    String getCustomerIdByName(String name) throws SQLException;
    String getCustomerNameById(String customerId) throws SQLException;
    ArrayList<CustomerDTO> getCustomerDTOsForReservation() throws SQLException;

}
