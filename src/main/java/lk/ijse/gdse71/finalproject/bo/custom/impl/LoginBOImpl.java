package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.LoginBO;
import lk.ijse.gdse71.finalproject.dao.custom.LoginDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.LoginDAOImpl;
import lk.ijse.gdse71.finalproject.dto.LoginDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginDAO = new LoginDAOImpl();
    @Override
    public boolean saveLogin(LoginDTO loginDTO) throws SQLException {
        return loginDAO.save(loginDTO);
    }

    public LoginDTO findByUserName(String userName) throws SQLException {
        return loginDAO.findByUserName(userName);
    }

    public boolean updateLogin(LoginDTO loginDTO) throws SQLException {
       return loginDAO.update(loginDTO);
    }

    @Override
    public boolean deleteLogin(String dto) throws SQLException {
       return false;
    }

    @Override
    public String getNextId() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<LoginDTO> getAll() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<LoginDTO> searchLoginDetails(String keyword) throws SQLException {
       return null;
    }

    public LoginDTO findByEmail(String email) throws SQLException {
       return loginDAO.findByEmail(email);
    }
}
