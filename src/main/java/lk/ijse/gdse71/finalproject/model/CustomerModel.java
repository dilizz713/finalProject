package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerModel {
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Customer values (?,?,?,?,?,?)",
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getPhoneNumber(),
                customerDTO.getNic()
        );
    }
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "update Customer set name=?, address=?, email=?, phoneNumber=?, nic=? where id=?",
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getPhoneNumber(),
                customerDTO.getNic(),
                customerDTO.getId()
        );
    }
    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute("delete from Customer where id=?", customerId);
    }

    public String getNextCustomerId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select id from Customer order by id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i+1;
            return String.format("C%03d",newId);
        }
        return "C001";
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Customer");

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
                   rst.getString(1),        //id
                   rst.getString(2),        //name
                   rst.getString(3),        //address
                   rst.getString(4),        //email
                   rst.getInt(5),           //phone
                   rst.getString(6)         //nic

            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public ArrayList<CustomerDTO> getCustomersBySearch(String keyword) throws SQLException {
        String searchQuery = "select * from Customer where id Like ? or name Like ? ";

        // Execute the query with the search keyword
        ResultSet rst = CrudUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%");


        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
                    rst.getString(1),        //id
                    rst.getString(2),        //name
                    rst.getString(3),        //address
                    rst.getString(4),        //email
                    rst.getInt(5),           //phone
                    rst.getString(6)         //nic

            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }



}
