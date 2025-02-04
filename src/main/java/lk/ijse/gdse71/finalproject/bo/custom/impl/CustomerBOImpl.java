package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.CustomerBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.CustomerDAO;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.save(new Customer(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getPhoneNumber(),
                customerDTO.getNic())
        );
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(new Customer(
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
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        for(Customer customer : customers){
            customerDTOS.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getPhoneNumber(),
                    customer.getNic()
            ));
        }
        return customerDTOS;
    }

    public ArrayList<CustomerDTO> searchCustomer(String keyword) throws SQLException {
       ArrayList<Customer> customers = customerDAO.search(keyword);
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        for(Customer customer : customers){
            customerDTOS.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getPhoneNumber(),
                    customer.getNic()
            ));
        }
        return customerDTOS;
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
        ArrayList<Customer> customers = customerDAO.getCustomerDTOsForReservation();
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        for(Customer customer : customers){
            customerDTOS.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName()
            ));
        }
        return customerDTOS;

    }
}
