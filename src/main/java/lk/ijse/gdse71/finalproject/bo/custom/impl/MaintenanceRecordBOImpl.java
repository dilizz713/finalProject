package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.MaintenanceRecordBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.MaintenanceRecordDAO;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.entity.MaintenanceRecord;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaintenanceRecordBOImpl implements MaintenanceRecordBO {
    MaintenanceRecordDAO maintenanceRecordDAO = (MaintenanceRecordDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MAINTENANCERECORD);

    public String getNextId() throws SQLException {
        return maintenanceRecordDAO.getNextId();
    }

    public ArrayList<MaintenanceRecordDTO> getAllMaintenanceRecords() throws SQLException {
        ArrayList<MaintenanceRecord> maintenanceRecords =  maintenanceRecordDAO.getAll();
        ArrayList<MaintenanceRecordDTO> maintenanceRecordDTOS = new ArrayList<>();

        for(MaintenanceRecord maintenanceRecord : maintenanceRecords){
            maintenanceRecordDTOS.add(new MaintenanceRecordDTO(
                    maintenanceRecord.getId(),
                    maintenanceRecord.getStartDate(),
                    maintenanceRecord.getEndDate(),
                    maintenanceRecord.getDescription(),
                    maintenanceRecord.getVehicleId(),
                    maintenanceRecord.getStatus()
            ));
        }
        return maintenanceRecordDTOS;
    }

    public ArrayList<MaintenanceRecordDTO> searchMaintenanceRecordDetails(String keyword) throws SQLException {
        return null;
    }


    public boolean saveMaintenanceRecords(MaintenanceRecordDTO maintenanceRecordDTO) throws SQLException {
        return maintenanceRecordDAO.save(new MaintenanceRecord(
                maintenanceRecordDTO.getId(),
                maintenanceRecordDTO.getStartDate(),
                maintenanceRecordDTO.getEndDate(),
                maintenanceRecordDTO.getDescription(),
                maintenanceRecordDTO.getVehicleId(),
                maintenanceRecordDTO.getStatus()
        ));
    }


    public boolean updateMaintenanceRecords(MaintenanceRecordDTO maintenanceRecordDTO) throws SQLException {
        return maintenanceRecordDAO.update(new MaintenanceRecord(
                maintenanceRecordDTO.getId(),
                maintenanceRecordDTO.getStartDate(),
                maintenanceRecordDTO.getEndDate(),
                maintenanceRecordDTO.getDescription(),
                maintenanceRecordDTO.getVehicleId(),
                maintenanceRecordDTO.getStatus()
        ));
    }

    public boolean deleteMaintenanceRecords(String recordId) throws SQLException {
        return maintenanceRecordDAO.delete(recordId);
    }


    public MaintenanceRecordDTO getMaintenanceRecordsById(String recordId) throws SQLException {
         MaintenanceRecord maintenanceRecord = maintenanceRecordDAO.getRecordsById(recordId);
         return new MaintenanceRecordDTO(
                 maintenanceRecord.getId(),
                 maintenanceRecord.getStartDate(),
                 maintenanceRecord.getEndDate(),
                 maintenanceRecord.getDescription(),
                 maintenanceRecord.getVehicleId(),
                 maintenanceRecord.getStatus()
         );

    }
}
