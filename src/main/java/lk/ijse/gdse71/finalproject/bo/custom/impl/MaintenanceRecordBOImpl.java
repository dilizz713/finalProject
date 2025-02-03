package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.MaintenanceRecordBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.MaintenanceRecordDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.MaintenanceRecordDAOImpl;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class MaintenanceRecordBOImpl implements MaintenanceRecordBO {
    MaintenanceRecordDAO maintenanceRecordDAO = (MaintenanceRecordDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MAINTENANCERECORD);

    public String getNextId() throws SQLException {
        return maintenanceRecordDAO.getNextId();
    }
    public ArrayList<MaintenanceRecordDTO> getAllMaintenanceRecords() throws SQLException {
        return maintenanceRecordDAO.getAll();
    }

    public ArrayList<MaintenanceRecordDTO> searchMaintenanceRecordDetails(String keyword) throws SQLException {
        return null;
    }




    public boolean saveMaintenanceRecords(MaintenanceRecordDTO maintenanceRecordDTO) throws SQLException {
       return maintenanceRecordDAO.save(maintenanceRecordDTO);
    }


    public boolean updateMaintenanceRecords(MaintenanceRecordDTO maintenanceRecordDTO) throws SQLException {
        return maintenanceRecordDAO.update(maintenanceRecordDTO);
    }

    public boolean deleteMaintenanceRecords(String recordId) throws SQLException {
       return maintenanceRecordDAO.delete(recordId);
    }


    public MaintenanceRecordDTO getMaintenanceRecordsById(String recordId) throws SQLException {
        return maintenanceRecordDAO.getRecordsById(recordId);

    }
}
