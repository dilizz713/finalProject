package lk.ijse.gdse71.finalproject.util;

import lk.ijse.gdse71.finalproject.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T execute(String sql, Object...obj) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) {
            pst.setObject((i+1),obj[i]);
        }
        if(sql.startsWith("select") || sql.startsWith("SELECT")){
            ResultSet rst = pst.executeQuery();
            return (T) rst;
        }else{
            int i = pst.executeUpdate();
            boolean isSaved = i > 0;
            return (T) ((Boolean) isSaved);
        }

    }

    public static <T> T execute(Connection connection, String sql, Object...obj) throws SQLException {
        PreparedStatement pst = connection.prepareStatement(sql);
        for (int i = 0; i < obj.length; i++) {
            pst.setObject((i + 1), obj[i]);
        }

        if(sql.startsWith("select") || sql.startsWith("SELECT")){
            ResultSet rst = pst.executeQuery();
            return (T) rst;
        } else {
            int affectedRows = pst.executeUpdate();
            return (T) ((Boolean) (affectedRows > 0));
        }


    }




}


