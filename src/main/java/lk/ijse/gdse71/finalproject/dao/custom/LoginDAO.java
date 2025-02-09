package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.entity.Login;

import java.sql.SQLException;

public interface LoginDAO extends CrudDAO<Login> {
    Login findByEmail(String email) throws SQLException;

    Login findByUserName(String userName) throws SQLException;

}
