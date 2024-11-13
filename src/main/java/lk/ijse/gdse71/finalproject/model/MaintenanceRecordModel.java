package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.controller.MileageTrackingController;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaintenanceRecordModel {
    public String getNextMaintenanceId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select id from MaintenanceRecord order by id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(2);
            int i = Integer. parseInt(subString);
            int newId = i+1;
            return String.format("MR%03d",newId);

        }
        return "MR001";
    }
    public ArrayList<MaintenanceRecordDTO> getAllMaintenanceRecords() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from MaintenanceRecord");

        ArrayList<MaintenanceRecordDTO> maintenanceRecordDTOS = new ArrayList<>();

        while (rst.next()) {
            MaintenanceRecordDTO maintenanceRecordDTO = new MaintenanceRecordDTO(
                    rst.getString(1),           //reservation id
                    rst.getDate(2),             // start date
                    rst.getDate(3),             // end date
                    rst.getString(4),           // desc
                    rst.getString(5),           // maintenance type
                    rst.getString(6)            // vehicle id

            );
            maintenanceRecordDTOS.add(maintenanceRecordDTO);
        }
        return maintenanceRecordDTOS;
    }

    public ArrayList<String> getAllVehicleIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Vehicle");
        ArrayList<String> vehicleId = new ArrayList<>();
        while (rst.next()) {
            vehicleId.add(rst.getString(1));
        }
        return vehicleId;
    }

    public String getVehicleNameById(String vehicleId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select model from Vehicle where id = ?", vehicleId);
        return rst.next() ? rst.getString(1) : null;
    }

    public boolean saveMaintenanceRecord(MaintenanceRecordDTO maintenanceRecordDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into MaintenanceRecord values (?,?,?,?,?,?)",
                maintenanceRecordDTO.getId(),
                maintenanceRecordDTO.getStartDate(),
                maintenanceRecordDTO.getEndDate(),
                maintenanceRecordDTO.getDescription(),
                maintenanceRecordDTO.getMaintenanceType(),
                maintenanceRecordDTO.getVehicleId()
        );
    }


}
