package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.bo.SuperBO;
import lk.ijse.gdse71.finalproject.dto.VehicleDamageDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDamageBO extends SuperBO {
    String getNextId() throws SQLException;
    ArrayList<VehicleDamageDTO> getAllVehcileDamageDetails() throws SQLException;
    ArrayList<VehicleDamageDTO> searchVehcileDamageDetails(String keyword) throws SQLException;
    boolean saveVehcileDamageDetails(VehicleDamageDTO vehicleDamageDTO) throws SQLException;
    boolean updateVehcileDamageDetails(VehicleDamageDTO vehicleDamageDTO) throws SQLException;
    boolean deleteVehcileDamageDetails(String damageId) throws SQLException;
    double getRepairCostByVehicleId(String vehicleId) throws SQLException;

}
