package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.CustomerBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.CustomerDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.save(new CustomerDTO(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getPhoneNumber(),
                customerDTO.getNic())
        );
    }
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(new CustomerDTO(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getPhoneNumber(),
                customerDTO.getNic())
        );
    }
    public boolean deleteCustomer(String customerId) throws SQLException {
        return customerDAO.delete(customerId);

    }

    public String getNextId() throws SQLException {
        return customerDAO.getNextId();
    }

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException {
       return customerDAO.getAll();
    }

    public ArrayList<CustomerDTO> searchCustomer(String keyword) throws SQLException {
        return customerDAO.search(keyword);
    }


    public ArrayList<String> getAllCustomerNames() throws SQLException {
        return customerDAO.getAllCustomerNames();

    }

    public String getCustomerIdByName(String name) throws SQLException {
        return customerDAO.getCustomerIdByName(name);
    }

    public String getCustomerNameById(String customerId) throws SQLException {
        return customerDAO.getCustomerNameById(customerId);
    }

    public ArrayList<CustomerDTO> getCustomerDTOsForReservation() throws SQLException {
       return customerDAO.getCustomerDTOsForReservation();
    }
}
