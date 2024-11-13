package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.dto.VehicleDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationModel {
    public String getNextReservationId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select id from Reservation order by id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i+1;
            return String.format("R%03d",newId);
        }
        return "R001";
    }
    public ArrayList<ReservationDTO> getAllReservations() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Reservation");

        ArrayList<ReservationDTO> reservationDTOS = new ArrayList<>();

        while (rst.next()) {
            ReservationDTO reservationDTO = new ReservationDTO(
                    rst.getString(1),       //reservation id
                    rst.getDate(2),         //start date
                    rst.getDate(3),         //end date
                    rst.getDouble(4),       //estimated mileage
                    rst.getString(5),       //customer id
                    rst.getString(6),       //vehicle id
                    rst.getString(7),       //driver id
                    rst.getString(8)      //status




            );
            reservationDTOS.add(reservationDTO);
        }
        return reservationDTOS;
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Customer");
        ArrayList<String> customerIds = new ArrayList<>();
        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }
    public ArrayList<String> getAllDriversIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Driver");
        ArrayList<String> driverId = new ArrayList<>();
        while (rst.next()) {
            driverId.add(rst.getString(1));
        }
        return driverId;
    }
    public ArrayList<String> getAllVehicleIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Vehicle");
        ArrayList<String> vehilceId = new ArrayList<>();
        while (rst.next()) {
            vehilceId.add(rst.getString(1));
        }
        return vehilceId;
    }

    public String getCustomerNameById(String customerId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select name from Customer where id = ?", customerId);
        return rst.next() ? rst.getString(1) : null;
    }

    public String getDriverNameById(String driverId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select name from Driver where id = ?", driverId);
        return rst.next() ? rst.getString(1) : null;
    }

    public String getVehicleNameById(String vehicleId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select model from Vehicle where id = ?", vehicleId);
        return rst.next() ? rst.getString(1) : null;
    }


    public boolean saveReservation(ReservationDTO reservationDTO) throws SQLException {
       return CrudUtil.execute(
                "insert into Reservation values (?,?,?,?,?,?,?,?)",
                reservationDTO.getId(),
                reservationDTO.getStartDate(),
                reservationDTO.getEndDate(),
                reservationDTO.getEstimatedMileage(),
                reservationDTO.getCustomerId(),
                reservationDTO.getVehicleId(),
                reservationDTO.getDriverId(),
                reservationDTO.getStatus()
        );

    }


    public boolean updateReservation(ReservationDTO reservationDTO) throws SQLException {
        return CrudUtil.execute(
                "update  Reservation set startDate=?, endDate=?, estimatedMileage=?, customerId=?, vehicleId=?, driverId=?, status=? where id=?",
                reservationDTO.getStartDate(),
                reservationDTO.getEndDate(),
                reservationDTO.getEstimatedMileage(),
                reservationDTO.getCustomerId(),
                reservationDTO.getVehicleId(),
                reservationDTO.getDriverId(),
                reservationDTO.getStatus(),
                reservationDTO.getId()
        );
    }

    public boolean deleteReservation(String reservationId) throws SQLException {
        return CrudUtil.execute("delete from Reservation where id=?",reservationId );
    }

    public ArrayList<ReservationDTO> getReservationsBySearch(String keyword) throws SQLException {
        String searchQuery = "select * from Reservation where id Like ? or vehicleId Like ? or driverId Like ?";
        ResultSet rst = CrudUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%","%" + keyword + "%");

        ArrayList<ReservationDTO> reservationDTOS = new ArrayList<>();

        while (rst.next()) {
            ReservationDTO reservationDTO = new ReservationDTO(
                    rst.getString(1),       //reservation id
                    rst.getDate(2),         //start date
                    rst.getDate(3),         //end date
                    rst.getDouble(4),       //estimated mileage
                    rst.getString(5),       //customer id
                    rst.getString(6),       //vehicle id
                    rst.getString(7),       //driver id
                    rst.getString(8)       // status

            );
            reservationDTOS.add(reservationDTO);
        }
        return reservationDTOS;
    }


}
