package lk.ijse.gdse71.finalproject.dao.custom.impl;

import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dao.custom.VehicleDAO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.entity.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select id from Vehicle order by id desc limit 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i + 1;
            return String.format("V%03d", newId);
        }
        return "V001";
    }
    @Override
    public ArrayList<Vehicle> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Vehicle");

        ArrayList<Vehicle> vehicleDTOS = new ArrayList<>();

        while (rst.next()) {
            Vehicle entity = new Vehicle(
                    rst.getString(1),     //id
                    rst.getString(2),     //company name
                    rst.getString(3),     // model
                    rst.getString(4),     // vehicle type
                    rst.getBytes(5),        // image
                    rst.getString(6),     // number plate
                    rst.getDouble(7),     //price
                    rst.getDate(8).toLocalDate()     //date


            );
            vehicleDTOS.add(entity);
        }
        return vehicleDTOS;
    }
    @Override
    public ArrayList<Vehicle> getVehiclesForPage(int start, int end) throws SQLException {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String query = "select * from Vehicle limit ?, ?";
        ResultSet resultSet = SQLUtil.execute(query, start, end - start); // end - start is the limit

        while (resultSet.next()) {
            Vehicle entity = new Vehicle(
                    resultSet.getString("id"),
                    resultSet.getString("make"),
                    resultSet.getString("model"),
                    resultSet.getString("vehicleType"),
                    resultSet.getBytes("image"),
                    resultSet.getString("numberPlate"),
                    resultSet.getDouble("price"),
                    resultSet.getDate("date").toLocalDate()
            );
            vehicles.add(entity);
        }

        return vehicles;
    }
    @Override
    public boolean save(Vehicle entity) throws SQLException {
        return SQLUtil.execute(
                "insert into Vehicle values (?,?,?,?,?,?,?,?)",
                entity.getId(),
                entity.getMake(),
                entity.getModel(),
                entity.getVehicleType(),
                entity.getImage(),
                entity.getNumberPlate(),
                entity.getPrice(),
                entity.getRegistrationDate()


        );
    }
    @Override
    public boolean update(Vehicle entity) throws SQLException {
        return SQLUtil.execute(
                "update Vehicle set  make=?, model=?, vehicleType=?, image=?, numberPlate=?, price=?, registrationDate=? where id=?",
                entity.getMake(),
                entity.getModel(),
                entity.getVehicleType(),
                entity.getImage(),
                entity.getNumberPlate(),
                entity.getPrice(),
                entity.getRegistrationDate(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String vehicleId) throws SQLException {
        return SQLUtil.execute("delete from Vehicle where id=?", vehicleId);
    }
    @Override
    public ArrayList<Vehicle> search(String keyword) throws SQLException {
        String searchQuery = "select * from Vehicle where vehicleType Like ? or model Like ? or id Like ?";
        ResultSet rst = SQLUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");

        ArrayList<Vehicle> vehicleDTOS = new ArrayList<>();
        while (rst.next()) {
            Vehicle entity = new Vehicle(
                    rst.getString(1),     //id
                    rst.getString(2),     //company name
                    rst.getString(3),     // model
                    rst.getString(4),     // vehicle type
                    rst.getBytes(5),        // image
                    rst.getString(6),     // number plate
                    rst.getDouble(7),       //price
                    rst.getDate(8).toLocalDate()      //date


            );
            vehicleDTOS.add(entity);
        }
        return vehicleDTOS;
    }

    @Override
    public ArrayList<String> getAllVehcileIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select id from Vehicle");
        ArrayList<String> vehilceId = new ArrayList<>();
        while (rst.next()) {
            vehilceId.add(rst.getString(1));
        }
        return vehilceId;

    }
    @Override
    public String getVehicleModelById(String vehicleId) throws SQLException {
        String sql = "select model from Vehicle where id = ?";
        ResultSet resultSet = SQLUtil.execute(sql, vehicleId);

        if (resultSet.next()) {
            return resultSet.getString("model");
        }
        return null;

    }
    @Override
    public String getVehiclePriceById(String vehicleID) throws SQLException {
        ResultSet rst = SQLUtil.execute("select price from Vehicle where id = ?", vehicleID);
        return rst.next() ? rst.getString(1) : null;
    }
    @Override
    public String getNumberPlateById(String vehicleId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select numberPlate from Vehicle where id = ?", vehicleId);
        return rst.next() ? rst.getString(1) : null;
    }
}
