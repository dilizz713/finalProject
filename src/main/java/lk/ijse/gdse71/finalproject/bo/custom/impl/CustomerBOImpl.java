package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.dao.custom.SQLUtil;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl {
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        CustomerDTO customerDTO = new CustomerDTO(customerDTO.getId(), name, address, email, phone, nic);
        boolean isSaved = customerDAO.save(customerDTO);
    }
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {

    }
    public boolean deleteCustomer(String customerId) throws SQLException {

    }

    public String getNextId() throws SQLException {

    }

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException {

    }

    public ArrayList<CustomerDTO> searchCustomer(String keyword) throws SQLException {

    }


    public ArrayList<String> getAllCustomerNames() throws SQLException {


    }

    public String getCustomerIdByName(String name) throws SQLException {

    }

    public String getCustomerNameById(String customerId) throws SQLException {

    }
}
