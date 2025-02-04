package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDAO extends CrudDAO<Vehicle> {
    ArrayList<Vehicle> getVehiclesForPage(int start, int end) throws SQLException;

    ArrayList<String> getAllVehcileIds() throws SQLException;

    String getVehicleModelById(String vehicleId) throws SQLException;

    String getVehiclePriceById(String vehicleID) throws SQLException;

    String getNumberPlateById(String vehicleId) throws SQLException;
}
