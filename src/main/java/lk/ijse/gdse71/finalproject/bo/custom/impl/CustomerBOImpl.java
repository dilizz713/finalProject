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

    @Override
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
    @Override
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
    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        return customerDAO.delete(customerId);

    }
    @Override
    public String getNextId() throws SQLException {
        return customerDAO.getNextId();
    }
    @Override
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
    @Override
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

    @Override
    public ArrayList<String> getAllCustomerNames() throws SQLException {
        return customerDAO.getAllCustomerNames();

    }
    @Override
    public String getCustomerIdByName(String name) throws SQLException {
        return customerDAO.getCustomerIdByName(name);
    }
    @Override
    public String getCustomerNameById(String customerId) throws SQLException {
        return customerDAO.getCustomerNameById(customerId);
    }
    @Override
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
