package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.entity.MaintenanceRecord;

import java.sql.SQLException;

public interface MaintenanceRecordDAO extends CrudDAO<MaintenanceRecord> {
    // ArrayList<String> getAllVehicleIds() throws SQLException;
    //  String getVehicleNameById(String vehicleId) throws SQLException;
    MaintenanceRecord getRecordsById(String recordId) throws SQLException;

}
