package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.VehicleBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.VehicleDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.VehicleDAOImpl;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {
    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VEHICLE);

    @Override
    public String getNextId() throws SQLException {
        return vehicleDAO.getNextId();
    }
    @Override
    public ArrayList<VehicleDTO> getAllVehicles() throws SQLException {
        ArrayList<Vehicle> vehicles =  vehicleDAO.getAll();
        ArrayList<VehicleDTO> vehicleDTOs = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            vehicleDTOs.add(new VehicleDTO(
                    vehicle.getId(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getImage(),
                    vehicle.getNumberPlate(),
                    vehicle.getPrice(),
                    vehicle.getRegistrationDate()
            ));
        }
        return vehicleDTOs;
    }


    @Override
    public boolean saveVehicles(VehicleDTO vehicleDTO) throws SQLException {
        return vehicleDAO.save(new Vehicle(
                vehicleDTO.getId(),
                vehicleDTO.getMake(),
                vehicleDTO.getModel(),
                vehicleDTO.getVehicleType(),
                vehicleDTO.getImage(),
                vehicleDTO.getNumberPlate(),
                vehicleDTO.getPrice(),
                vehicleDTO.getRegistrationDate()
        ));
    }
    @Override
    public boolean updateVehicles(VehicleDTO vehicleDTO) throws SQLException {
        return vehicleDAO.update(new Vehicle(
                vehicleDTO.getId(),
                vehicleDTO.getMake(),
                vehicleDTO.getModel(),
                vehicleDTO.getVehicleType(),
                vehicleDTO.getImage(),
                vehicleDTO.getNumberPlate(),
                vehicleDTO.getPrice(),
                vehicleDTO.getRegistrationDate()
        ));
    }

    @Override
    public boolean deleteVehicles(String vehicleId) throws SQLException {
        return vehicleDAO.delete(vehicleId);
    }
    @Override
    public ArrayList<VehicleDTO> searchVehicles(String keyword) throws SQLException {
        ArrayList<Vehicle> vehicles =  vehicleDAO.search(keyword);
        ArrayList<VehicleDTO> vehicleDTOs = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            vehicleDTOs.add(new VehicleDTO(
                    vehicle.getId(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getImage(),
                    vehicle.getNumberPlate(),
                    vehicle.getPrice(),
                    vehicle.getRegistrationDate()
            ));
        }
        return vehicleDTOs;
    }

    @Override
    public ArrayList<String> getAllVehcileIds() throws SQLException {
        ArrayList<String> vehicles =  vehicleDAO.getAllVehcileIds();
        ArrayList<String> vehicleIds = new ArrayList<>();

        for (String vehicleId : vehicles) {
            vehicleIds.add(vehicleId);
        }
        return vehicleIds;

    }
    @Override
    public String getVehicleModelById(String vehicleId) throws SQLException {
        return vehicleDAO.getVehicleModelById(vehicleId);

    }
    @Override
    public String getVehiclePriceById(String vehicleID) throws SQLException {
        return vehicleDAO.getVehiclePriceById(vehicleID);
    }
    @Override
    public String getNumberPlateById(String vehicleId) throws SQLException {
        return vehicleDAO.getNumberPlateById(vehicleId);
    }
}
