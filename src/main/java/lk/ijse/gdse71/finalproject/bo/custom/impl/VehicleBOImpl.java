package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.VehicleBO;
import lk.ijse.gdse71.finalproject.dao.custom.VehicleDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.VehicleDAOImpl;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {
    VehicleDAO vehicleDAO = new VehicleDAOImpl();
    public String getNextId() throws SQLException {
        return vehicleDAO.getNextId();
    }
    public ArrayList<VehicleDTO> getAllVehicles() throws SQLException {
        return vehicleDAO.getAll();
    }

    public ArrayList<VehicleDTO> getVehiclesForPage(int start, int end) throws SQLException {
        return null;
    }

    public boolean saveVehicles(VehicleDTO vehicleDTO) throws SQLException {
        return vehicleDAO.save(vehicleDTO);
    }

    public boolean updateVehicles(VehicleDTO vehicleDTO) throws SQLException {
        return vehicleDAO.update(vehicleDTO);
    }


    public boolean deleteVehicles(String vehicleId) throws SQLException {
        return vehicleDAO.delete(vehicleId);
    }

    public ArrayList<VehicleDTO> searchVehicles(String keyword) throws SQLException {
        return vehicleDAO.search(keyword);
    }


    public ArrayList<String> getAllVehcileIds() throws SQLException {
        return vehicleDAO.getAllVehcileIds();

    }

    public String getVehicleModelById(String vehicleId) throws SQLException {
        return vehicleDAO.getVehicleModelById(vehicleId);

    }
    public String getVehiclePriceById(String vehicleID) throws SQLException {
        return vehicleDAO.getVehiclePriceById(vehicleID);
    }

    public String getNumberPlateById(String vehicleId) throws SQLException {
        return vehicleDAO.getNumberPlateById(vehicleId);
    }
}
