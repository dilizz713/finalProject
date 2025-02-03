package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MaintenanceRecordDAO extends CrudDAO<MaintenanceRecordDTO> {
   // ArrayList<String> getAllVehicleIds() throws SQLException;
  //  String getVehicleNameById(String vehicleId) throws SQLException;
    MaintenanceRecordDTO getRecordsById(String recordId) throws SQLException;

}
