package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.VehicleDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDAO extends CrudDAO<VehicleDTO> {
    ArrayList<VehicleDTO> getVehiclesForPage(int start, int end) throws SQLException;
    ArrayList<String> getAllVehcileIds() throws SQLException;
    String getVehicleModelById(String vehicleId) throws SQLException;
}
