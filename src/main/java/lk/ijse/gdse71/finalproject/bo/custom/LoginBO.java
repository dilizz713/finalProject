package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.bo.SuperBO;
import lk.ijse.gdse71.finalproject.dto.LoginDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginBO extends SuperBO {
    boolean saveLogin(LoginDTO loginDTO) throws SQLException;

    LoginDTO findByUserName(String userName) throws SQLException;

    String getNextId() throws SQLException;

    ArrayList<LoginDTO> getAll() throws SQLException;

    LoginDTO findByEmail(String email) throws SQLException;

    boolean updatePasswordByEmail(String emailText, String password) throws SQLException;
}
