package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.DriverDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverModel {
    public boolean saveDriver(DriverDTO driverDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Driver values (?,?,?,?,?)",
                driverDTO.getId(),
                driverDTO.getName(),
                driverDTO.getLicenseNumber(),
                driverDTO.getPhoneNumber(),
                driverDTO.getNic()
        );
    }

    public boolean updateDriver(DriverDTO driverDTO) throws SQLException {
        return CrudUtil.execute(
                "update Driver set name=?, licenseNumber=?, phoneNumber=?, nic=? where id=?",
                driverDTO.getName(),
                driverDTO.getLicenseNumber(),
                driverDTO.getPhoneNumber(),
                driverDTO.getNic(),
                driverDTO.getId()
        );
    }
    public boolean deleteDriver(String driverId) throws SQLException {
        return CrudUtil.execute("delete from Driver where id=?", driverId);
    }

    public String getNextDriverId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select id from Driver order by id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i+1;
            return String.format("D%03d",newId);
        }
        return "D001";
    }

    public ArrayList<DriverDTO> getAllDrivers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Driver");

        ArrayList<DriverDTO> driverDTOS = new ArrayList<>();

        while (rst.next()) {
            DriverDTO driverDTO = new DriverDTO(
                    rst.getString(1),        //id
                    rst.getString(2),        //name
                    rst.getString(3),        //license number
                    rst.getInt(4),           //phone
                    rst.getString(5)         //nic

            );
            driverDTOS.add(driverDTO);
        }
        return driverDTOS;
    }

    public ArrayList<DriverDTO> getDriverBySearch(String keyword) throws SQLException {
        String searchQuery = "select * from Driver where id Like ? or name Like ?";
        ResultSet rst = CrudUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%");

        ArrayList<DriverDTO> driverDTOS = new ArrayList<>();

        while (rst.next()) {
            DriverDTO driverDTO = new DriverDTO(
                    rst.getString(1),        //id
                    rst.getString(2),        //name
                    rst.getString(3),        //license number
                    rst.getInt(4),           //phone
                    rst.getString(5)         //nic

            );
            driverDTOS.add(driverDTO);
        }
        return driverDTOS;
    }
}
