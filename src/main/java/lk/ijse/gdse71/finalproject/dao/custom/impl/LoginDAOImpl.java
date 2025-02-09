package lk.ijse.gdse71.finalproject.dao.custom.impl;

import lk.ijse.gdse71.finalproject.dao.custom.LoginDAO;
import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dto.LoginDTO;
import lk.ijse.gdse71.finalproject.entity.Login;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
    public boolean save(Login entity) throws SQLException {
        return SQLUtil.execute(
                "insert into Login values(?,?,?)",
                entity.getUserName(),
                entity.getPassword(),
                entity.getEmail()
        );
    }

    public Login findByUserName(String userName) throws SQLException {
        String query = "select * from Login where userName=?";
        ResultSet rst = SQLUtil.execute(query, userName);

        if (rst.next()) {
            return new Login(
                    rst.getString("userName"),
                    rst.getString("password"),
                    rst.getString("email")
            );
        }
       else{
           return null;
        }


    }

    @Override
    public boolean updatePasswordByEmail(String emailText, String newPassword) throws SQLException {
        return SQLUtil.execute("update Login set password = ? where email = ?", newPassword, emailText);
    }

    public boolean update(Login entity) throws SQLException {
       return false;
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
    public ArrayList<Login> getAll() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Login> search(String keyword) throws SQLException {
        return null;
    }

    public Login findByEmail(String email) throws SQLException {
        String query = "select * from Login where email=?";
        ResultSet rst = SQLUtil.execute(query, email);

        if (rst.next()) {
            return new Login(
                    rst.getString("userName"),
                    rst.getString("password"),
                    rst.getString("email")
            );
        }
       else{
           return null;
        }
    }
}
