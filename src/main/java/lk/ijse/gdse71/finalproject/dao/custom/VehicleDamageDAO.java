package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.entity.VehicleDamage;

import java.sql.SQLException;

public interface VehicleDamageDAO extends CrudDAO<VehicleDamage> {
    double getRepairCostByVehicleId(String vehicleId) throws SQLException;
}
