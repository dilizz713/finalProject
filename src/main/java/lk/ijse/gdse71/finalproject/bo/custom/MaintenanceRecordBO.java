package lk.ijse.gdse71.finalproject.bo.custom;

import lk.ijse.gdse71.finalproject.bo.SuperBO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MaintenanceRecordBO extends SuperBO {
    String getNextId() throws SQLException;
    ArrayList<MaintenanceRecordDTO> getAllMaintenanceRecords() throws SQLException;
    boolean saveMaintenanceRecords(MaintenanceRecordDTO maintenanceRecordDTO) throws SQLException;
    boolean updateMaintenanceRecords(MaintenanceRecordDTO maintenanceRecordDTO) throws SQLException;
    boolean deleteMaintenanceRecords(String recordId) throws SQLException;
    MaintenanceRecordDTO getMaintenanceRecordsById(String recordId) throws SQLException;

}
