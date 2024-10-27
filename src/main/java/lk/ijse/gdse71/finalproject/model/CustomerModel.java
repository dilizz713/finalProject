package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


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

    public String getNextCustomerId() throws SQLException {
        ResultSet resultSet =  CrudUtil.execute(
                "select id from Customer order by id desc limit 1"
        );

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i+1;
            return String.format("C%03d",newId);
        }
        return "C001";
    }


}
