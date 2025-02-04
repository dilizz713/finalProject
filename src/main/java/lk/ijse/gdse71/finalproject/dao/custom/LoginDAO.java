package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.LoginDTO;

import java.sql.SQLException;

public interface LoginDAO extends CrudDAO<LoginDTO> {
    LoginDTO findByEmail(String email) throws SQLException;

    LoginDTO findByUserName(String userName) throws SQLException;

}
