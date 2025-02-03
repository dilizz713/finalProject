package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.bo.SuperBO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleBO extends SuperBO {
    String getNextId() throws SQLException;
    ArrayList<VehicleDTO> getAllVehicles() throws SQLException;
    ArrayList<VehicleDTO> getVehiclesForPage(int start, int end) throws SQLException;
    boolean saveVehicles(VehicleDTO vehicleDTO) throws SQLException;
    boolean updateVehicles(VehicleDTO vehicleDTO) throws SQLException;
    boolean deleteVehicles(String vehicleId) throws SQLException;
    ArrayList<VehicleDTO> searchVehicles(String keyword) throws SQLException;
    ArrayList<String> getAllVehcileIds() throws SQLException;
    String getVehicleModelById(String vehicleId) throws SQLException;
    String getVehiclePriceById(String vehicleID) throws SQLException;
    String getNumberPlateById(String vehicleId) throws SQLException;

}
