package lk.ijse.gdse71.finalproject.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    boolean save(T dto) throws SQLException;

    boolean update(T dto) throws SQLException;

    boolean delete(String dto) throws SQLException;

    String getNextId() throws SQLException;

    ArrayList<T> getAll() throws SQLException;

    ArrayList<T> search(String keyword) throws SQLException;

}
