package lk.ijse.gdse71.finalproject.dao.custom.impl;

import lk.ijse.gdse71.finalproject.dao.custom.LoginDAO;
import lk.ijse.gdse71.finalproject.dao.custom.SQLUtil;
import lk.ijse.gdse71.finalproject.dto.LoginDTO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
    public boolean save(LoginDTO loginDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into Login values(?,?,?)",
                loginDTO.getUserName(),
                loginDTO.getPassword(),
                loginDTO.getEmail()
        );
    }

    public LoginDTO findByUserName(String userName) throws SQLException {
        String query = "select * from Login where userName=?";
        ResultSet rst = SQLUtil.execute(query,userName);


        if(rst.next()){
            return new LoginDTO(
                    rst.getString("userName"),
                    rst.getString("password"),
                    rst.getString("email")
            );
        }
        return null;



    }

    public boolean update(LoginDTO loginDTO) throws SQLException {
        return SQLUtil.execute(
                "update  Login set  password = ? , email = ? where userName=?",
                loginDTO.getPassword(),
                loginDTO.getEmail(),
                loginDTO.getUserName()
        );
    }

    @Override
    public boolean delete(String dto) throws SQLException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<LoginDTO> getAll() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<LoginDTO> search(String keyword) throws SQLException {
        return null;
    }

    public LoginDTO findByEmail(String email) throws SQLException {
        String query = "select * from Login where email=?";
        ResultSet rst = SQLUtil.execute(query,email);


        if(rst.next()){
            return new LoginDTO(
                    rst.getString("userName"),
                    rst.getString("password"),
                    rst.getString("email")
            );
        }
        return null;
    }
}
