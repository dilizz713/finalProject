package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.VehicleDamageBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.VehicleDamageDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.VehicleDamageDAOImpl;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDamageDTO;
import lk.ijse.gdse71.finalproject.entity.VehicleDamage;
import lk.ijse.gdse71.finalproject.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDamageBOImpl implements VehicleDamageBO {
    VehicleDamageDAO vehicleDamageDAO = (VehicleDamageDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VEHICLEDAMAGE);

    @Override
    public String getNextId() throws SQLException {
        return vehicleDamageDAO.getNextId();
    }
    @Override
    public ArrayList<VehicleDamageDTO> getAllVehcileDamageDetails() throws SQLException {
        ArrayList<VehicleDamage> vehicles =  vehicleDamageDAO.getAll();
        ArrayList<VehicleDamageDTO> vehicleDamageDTOs = new ArrayList<>();

        for (VehicleDamage vehicle : vehicles) {
            vehicleDamageDTOs.add(new VehicleDamageDTO(
                    vehicle.getId(),
                    vehicle.getDescription(),
                    vehicle.getReportedDate(),
                    vehicle.getRepairCost(),
                    vehicle.getVehicleId()
            ));
        }
        return vehicleDamageDTOs;
    }

    @Override
    public boolean saveVehcileDamageDetails(VehicleDamageDTO vehicleDamageDTO) throws SQLException {
        return vehicleDamageDAO.save(new VehicleDamage(
                vehicleDamageDTO.getId(),
                vehicleDamageDTO.getDescription(),
                vehicleDamageDTO.getReportedDate(),
                vehicleDamageDTO.getRepairCost(),
                vehicleDamageDTO.getVehicleId()
        ));
    }
    @Override
    public boolean updateVehcileDamageDetails(VehicleDamageDTO vehicleDamageDTO) throws SQLException {
        return vehicleDamageDAO.update(new VehicleDamage(
                vehicleDamageDTO.getId(),
                vehicleDamageDTO.getDescription(),
                vehicleDamageDTO.getReportedDate(),
                vehicleDamageDTO.getRepairCost(),
                vehicleDamageDTO.getVehicleId()
        ));
    }
    @Override
    public boolean deleteVehcileDamageDetails(String damageId) throws SQLException {
        return vehicleDamageDAO.delete(damageId);

    }
    @Override
    public double getRepairCostByVehicleId(String vehicleId) throws SQLException {
        return vehicleDamageDAO.getRepairCostByVehicleId(vehicleId);
    }
}
