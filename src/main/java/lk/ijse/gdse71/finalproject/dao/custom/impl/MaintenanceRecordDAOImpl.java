package lk.ijse.gdse71.finalproject.dao.custom.impl;

import lk.ijse.gdse71.finalproject.dao.custom.MaintenanceRecordDAO;
import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dto.MaintenanceRecordDTO;
import lk.ijse.gdse71.finalproject.entity.MaintenanceRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MaintenanceRecordDAOImpl implements MaintenanceRecordDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select id from MaintenanceRecord order by id desc limit 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(2);
            int i = Integer.parseInt(subString);
            int newId = i + 1;
            return String.format("MR%03d", newId);

        }
        return "MR001";
    }
    @Override
    public ArrayList<MaintenanceRecord> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from MaintenanceRecord");

        ArrayList<MaintenanceRecord> maintenanceRecordDTOS = new ArrayList<>();

        while (rst.next()) {
            LocalDate endDate = rst.getDate(3) != null ? rst.getDate(3).toLocalDate() : null;

            MaintenanceRecord entity = new MaintenanceRecord(
                    rst.getString(1),                           //reservation id
                    rst.getDate(2).toLocalDate(),             // start date
                    endDate,                                            // end date
                    rst.getString(4),                          // desc
                    rst.getString(5),                        // vehicle id
                    rst.getString(6)                        //status

            );
            maintenanceRecordDTOS.add(entity);
        }
        return maintenanceRecordDTOS;
    }

    @Override
    public ArrayList<MaintenanceRecord> search(String keyword) throws SQLException {
        return null;
    }

    @Override
    public boolean save(MaintenanceRecord entity) throws SQLException {
        return SQLUtil.execute(
                "insert into MaintenanceRecord values (?,?,?,?,?,?)",
                entity.getId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getDescription(),
                entity.getVehicleId(),
                entity.getStatus()
        );
    }

    @Override
    public boolean update(MaintenanceRecord entity) throws SQLException {
        return SQLUtil.execute(
                "update  MaintenanceRecord set startDate =?, endDate=?, description=?, vehicleId=?, status=?  where id=?",
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getDescription(),
                entity.getVehicleId(),
                entity.getStatus(),
                entity.getId()
        );
    }
    @Override
    public boolean delete(String recordId) throws SQLException {
        return SQLUtil.execute("delete from MaintenanceRecord where id=?", recordId);
    }

    @Override
    public MaintenanceRecord getRecordsById(String recordId) throws SQLException {
        String query = "select * from MaintenanceRecord where id=?";
        ResultSet rst = SQLUtil.execute(query, recordId);

        if (rst.next()) {
            String id = rst.getString("id");
            LocalDate startDate = rst.getDate("startDate").toLocalDate();
            LocalDate endDate = rst.getDate("endDate") != null ? rst.getDate("EndDate").toLocalDate() : null;
            String description = rst.getString("description");
            String vehicleId = rst.getString("vehicleId");
            String status = rst.getString("status");


            return new MaintenanceRecord(id, startDate, endDate, description, vehicleId, status);

        }
        return null;

    }
}
