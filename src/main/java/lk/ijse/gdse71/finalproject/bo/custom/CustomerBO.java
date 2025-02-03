package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO {
    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;
    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
    boolean deleteCustomer(String customerId) throws SQLException;
    String getNextId() throws SQLException;
    ArrayList<CustomerDTO> getAllCustomer() throws SQLException;
    ArrayList<CustomerDTO> searchCustomer(String keyword) throws SQLException;
    ArrayList<String> getAllCustomerNames() throws SQLException;
    String getCustomerIdByName(String name) throws SQLException;
    String getCustomerNameById(String customerId) throws SQLException;
    ArrayList<CustomerDTO> getCustomerDTOsForReservation() throws SQLException;

}
