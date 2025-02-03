package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.dto.LoginDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginBO {
    boolean saveLogin(LoginDTO loginDTO) throws SQLException;
    LoginDTO findByUserName(String userName) throws SQLException;
    boolean updateLogin(LoginDTO loginDTO) throws SQLException;
    boolean deleteLogin(String dto) throws SQLException;
    String getNextId() throws SQLException;
    ArrayList<LoginDTO> getAll() throws SQLException;
    ArrayList<LoginDTO> searchLoginDetails(String keyword) throws SQLException;
    LoginDTO findByEmail(String email) throws SQLException;
}
