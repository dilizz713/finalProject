package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.VehicleDamageDTO;

import java.sql.SQLException;

public interface VehicleDamageDAO extends CrudDAO<VehicleDamageDTO> {
  //  String getCustomerIdByVehicleId(String vehicleId) throws SQLException;
    double getRepairCostByVehicleId(String vehicleId) throws SQLException;
}
