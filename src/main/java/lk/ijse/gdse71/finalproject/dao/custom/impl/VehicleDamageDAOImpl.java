package lk.ijse.gdse71.finalproject.dao.custom.impl;

import lk.ijse.gdse71.finalproject.dao.custom.SQLUtil;
import lk.ijse.gdse71.finalproject.dao.custom.VehicleDamageDAO;
import lk.ijse.gdse71.finalproject.dto.VehicleDamageDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class VehicleDamageDAOImpl implements VehicleDamageDAO {
    public String getNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select id from VehicleDamage order by id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i+1;
            return String.format("D%03d",newId);
        }
        return "D001";
    }

    public ArrayList<VehicleDamageDTO> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from VehicleDamage");

        ArrayList<VehicleDamageDTO> vehicleDamageDTOS = new ArrayList<>();

        while (rst.next()) {
            LocalDate date = rst.getDate("reportedDate") != null
                    ? rst.getDate("reportedDate").toLocalDate()
                    : null;

            vehicleDamageDTOS.add(new VehicleDamageDTO(
                    rst.getString(1),                       //damage id
                    rst.getString(2),                       //description
                    date,                                                //reported dated
                    rst.getDouble(4),                       // repair cost
                    rst.getString(5)                       //vehicle id

            ));

        }

        return vehicleDamageDTOS;
    }

    @Override
    public ArrayList<VehicleDamageDTO> search(String keyword) throws SQLException {
        return null;
    }


   /* public String getCustomerIdByVehicleId(String vehicleId) throws SQLException {
        String sql = "select customerId from Reservation where vehicleId = ?";
        ResultSet resultSet = SQLUtil.execute(sql, vehicleId);

        if (resultSet.next()) {
            return resultSet.getString("customerId");
        }
        return null;
    }*/

    public boolean save(VehicleDamageDTO vehicleDamageDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into VehicleDamage values (?,?,?,?,?)",
                vehicleDamageDTO.getId(),
                vehicleDamageDTO.getDescription(),
                vehicleDamageDTO.getReportedDate(),
                vehicleDamageDTO.getRepairCost(),
                vehicleDamageDTO.getVehicleId()

        );
    }

    public boolean update(VehicleDamageDTO vehicleDamageDTO) throws SQLException {
        return SQLUtil.execute(
                "update VehicleDamage set description=?, reportedDate=?, repairCost=?, vehicleId=? where id=?",
                vehicleDamageDTO.getDescription(),
                vehicleDamageDTO.getReportedDate(),
                vehicleDamageDTO.getRepairCost(),
                vehicleDamageDTO.getVehicleId(),
                vehicleDamageDTO.getId()

        );
    }

    public boolean delete(String damageId) throws SQLException {
        return SQLUtil.execute("delete from VehicleDamage where id=?", damageId);

    }

    public double getRepairCostByVehicleId(String vehicleId) throws SQLException {
        String query = "SELECT SUM(repairCost) AS totalRepairCost FROM VehicleDamage WHERE vehicleId = ?";
        ResultSet rst = SQLUtil.execute(query, vehicleId);

        if (rst.next()) {
            return rst.getDouble("totalRepairCost");
        }
        return 0.0;
    }
}
