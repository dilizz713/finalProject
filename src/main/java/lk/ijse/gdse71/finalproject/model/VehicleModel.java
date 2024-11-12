package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.dto.tm.VehicleTM;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleModel {
    public String getNextVehicleId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select id from Vehicle order by id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i+1;
            return String.format("V%03d",newId);
        }
        return "V001";
    }
    public ArrayList<VehicleDTO> getAllVehicles() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Vehicle");

        ArrayList<VehicleDTO> vehicleDTOS = new ArrayList<>();

        while (rst.next()) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                          rst.getString(1),     //id
                          rst.getString(2),     //reg number
                          rst.getString(3),     // company name
                          rst.getString(4),     // model
                          rst.getInt(5),        // year
                          rst.getDouble(6),     // mileage
                          rst.getString(7),     // status
                          rst.getString(8)      // vehicle type



            );
            vehicleDTOS.add(vehicleDTO);
        }
        return vehicleDTOS;
    }

    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Vehicle values (?,?,?,?,?,?,?,?)",
                vehicleDTO.getId(),
                vehicleDTO.getRegistrationNumber(),
                vehicleDTO.getMake(),
                vehicleDTO.getModel(),
                vehicleDTO.getYear(),
                vehicleDTO.getMileage(),
                vehicleDTO.getStatus(),
                vehicleDTO.getVehicleType()
        );
    }
    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException {
        return CrudUtil.execute(
                "update Vehicle set registrationNumber =?, make=?, model=?, year=?, mileage=?, status=?, vehicleType=? where id=?",
                vehicleDTO.getRegistrationNumber(),
                vehicleDTO.getMake(),
                vehicleDTO.getModel(),
                vehicleDTO.getYear(),
                vehicleDTO.getMileage(),
                vehicleDTO.getStatus(),
                vehicleDTO.getVehicleType(),
                vehicleDTO.getId()
        );
    }
    public boolean deleteVehicle(String vehicleId) throws SQLException {
        return CrudUtil.execute("delete from Vehicle where id=?",vehicleId );
    }

    public ArrayList<VehicleDTO> getVehiclesBySearch(String keyword) throws SQLException {
       String searchQuery = "select * from Vehicle where vehicleType Like ? or model Like ?";
       ResultSet rst = CrudUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%");

        ArrayList<VehicleDTO> vehicleDTOS = new ArrayList<>();

        while (rst.next()) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                    rst.getString(1),     //id
                    rst.getString(2),     //reg number
                    rst.getString(3),     // company name
                    rst.getString(4),     // model
                    rst.getInt(5),        // year
                    rst.getDouble(6),     // mileage
                    rst.getString(7),     // status
                    rst.getString(8)      // vehicle type

            );
            vehicleDTOS.add(vehicleDTO);
        }
        return vehicleDTOS;
    }


}
