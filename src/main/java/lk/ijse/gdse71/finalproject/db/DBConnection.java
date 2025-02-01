package lk.ijse.gdse71.finalproject.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection dbConnection;
    public Connection connection;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/vehiclerental",
                "root",
                "Asd713@#"
        );
    }

    public  static  DBConnection getInstance() throws SQLException {
        if(dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }




}

//jdbc:mysql://localhost:3306/VehicleRental

