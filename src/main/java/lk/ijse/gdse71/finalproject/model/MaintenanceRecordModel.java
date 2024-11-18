package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.controller.MileageTrackingController;
import lk.ijse.gdse71.finalproject.dto.*;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

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
            LocalDate endDate = rst.getDate(3) != null ? rst.getDate(3).toLocalDate() : null;

            MaintenanceRecordDTO maintenanceRecordDTO = new MaintenanceRecordDTO(
                    rst.getString(1),                           //reservation id
                    rst.getDate(2).toLocalDate(),             // start date
                    endDate,                                            // end date
                    rst.getString(4),                          // desc
                    rst.getString(5),                        // vehicle id
                    rst.getString(6)                        //status

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
                maintenanceRecordDTO.getVehicleId(),
                maintenanceRecordDTO.getStatus()
        );
    }


    public boolean updateMaintenanceRecord(MaintenanceRecordDTO maintenanceRecordDTO) throws SQLException {
        return CrudUtil.execute(
                "update  MaintenanceRecord set startDate =?, endDate=?, description=?, vehicleId=?, status=?  where id=?",
                maintenanceRecordDTO.getStartDate(),
                maintenanceRecordDTO.getEndDate(),
                maintenanceRecordDTO.getDescription(),
                maintenanceRecordDTO.getVehicleId(),
                maintenanceRecordDTO.getStatus(),
                maintenanceRecordDTO.getId()
        );
    }

    public boolean deleteRecord(String recordId) throws SQLException {
        return CrudUtil.execute("delete from MaintenanceRecord where id=?",recordId );
    }
    public ArrayList<MaintenanceRecordDTO> getRecordsBySearch(String keyword) throws SQLException {
        String searchQuery = """
                select M.* , V.model 
                from MaintenanceRecord M
                join Vehicle V 
                on M.vehicleId = V.id
                where M.id Like ? or M.vehicleId Like ?  or V.model Like ? 
                """;
        ResultSet rst = CrudUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%","%" + keyword + "%");

        ArrayList<MaintenanceRecordDTO> maintenanceRecordDTOS = new ArrayList<>();

        while (rst.next()) {
            MaintenanceRecordDTO maintenanceRecordDTO = new MaintenanceRecordDTO(
                    rst.getString(1),                           //reservation id
                    rst.getDate(2).toLocalDate(),             // start date
                    rst.getDate(3).toLocalDate(),             // end date
                    rst.getString(4),                          // desc
                    rst.getString(5),                        // vehicle id
                    rst.getString(6)                        //status

            );
            maintenanceRecordDTOS.add(maintenanceRecordDTO);
        }
        return maintenanceRecordDTOS;
    }


    public MaintenanceRecordDTO getRecordsById(String recordId) throws SQLException {
        String query = "select * from MaintenanceRecord where id=?";
        ResultSet rst = CrudUtil.execute(query,recordId);

        if(rst.next()){
            String id = rst.getString("id");
            LocalDate startDate = rst.getDate("startDate").toLocalDate();
            LocalDate endDate = rst.getDate("endDate") != null ? rst.getDate("EndDate").toLocalDate() : null;
            String description = rst.getString("description");
            String vehicleId = rst.getString("vehicleId");
            String status = rst.getString("status");


            return new MaintenanceRecordDTO(id,startDate,endDate,description,vehicleId,status);

        }
        return null;

    }
}
