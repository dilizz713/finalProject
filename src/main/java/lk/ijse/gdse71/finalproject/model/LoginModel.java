package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.LoginDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    public boolean saveSignupDetails(LoginDTO loginDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Login values(?,?)",
                loginDTO.getUserName(),
                loginDTO.getPassword()
        );
    }

    public LoginDTO findByUserName(String userName) throws SQLException {
        String query = "select * from Login where userName=?";
        ResultSet rst = CrudUtil.execute(query,userName);


        if(rst.next()){
            return new LoginDTO(
                    rst.getString("userName"),
                    rst.getString("password")
            );
        }
        return null;



    }
}
