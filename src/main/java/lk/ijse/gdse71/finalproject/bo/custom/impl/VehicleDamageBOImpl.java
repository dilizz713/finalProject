package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.VehicleDamageBO;
import lk.ijse.gdse71.finalproject.dao.custom.VehicleDamageDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.VehicleDamageDAOImpl;
import lk.ijse.gdse71.finalproject.dto.VehicleDamageDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDamageBOImpl implements VehicleDamageBO {
    VehicleDamageDAO vehicleDamageDAO = new VehicleDamageDAOImpl();

    public String getNextId() throws SQLException {
       return vehicleDamageDAO.getNextId();
    }

    public ArrayList<VehicleDamageDTO> getAllVehcileDamageDetails() throws SQLException {
        return vehicleDamageDAO.getAll();
    }

    @Override
    public ArrayList<VehicleDamageDTO> searchVehcileDamageDetails(String keyword) throws SQLException {
        return null;
    }


    public boolean saveVehcileDamageDetails(VehicleDamageDTO vehicleDamageDTO) throws SQLException {
       return vehicleDamageDAO.save(vehicleDamageDTO);
    }

    public boolean updateVehcileDamageDetails(VehicleDamageDTO vehicleDamageDTO) throws SQLException {
       return vehicleDamageDAO.update(vehicleDamageDTO);
    }

    public boolean deleteVehcileDamageDetails(String damageId) throws SQLException {
       return vehicleDamageDAO.delete(damageId);

    }

    public double getRepairCostByVehicleId(String vehicleId) throws SQLException {
        return vehicleDamageDAO.getRepairCostByVehicleId(vehicleId);
    }
}
