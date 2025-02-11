package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.entity.MaintenanceRecord;

import java.sql.SQLException;

public interface MaintenanceRecordDAO extends CrudDAO<MaintenanceRecord> {
    MaintenanceRecord getRecordsById(String recordId) throws SQLException;

}
