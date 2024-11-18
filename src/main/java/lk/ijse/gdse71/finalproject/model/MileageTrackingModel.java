package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MileageTrackingModel {

    public String getNextTrackingId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select id from MileageTracking order by id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i+1;
            return String.format("M%03d",newId);
        }
        return "M001";
    }

    public ArrayList<MileageTrackingDTO> getAllTrakingDetails() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from MileageTracking");

        ArrayList<MileageTrackingDTO> mileageTrackingDTOS = new ArrayList<>();

        while (rst.next()) {
            MileageTrackingDTO mileageTrackingDTO = new MileageTrackingDTO(
                    rst.getString(1),       //tracking id
                    rst.getDouble(2),       //estimated mileage
                    rst.getDouble(3),       //actual mileage
                    rst.getDouble(4),       //extra charges per km
                    rst.getDouble(5),       //total extra charges
                    rst.getString(6),       //reservation id
                    rst.getDouble(7),       //start date mileage
                    rst.getDouble(8),       //end date mileage
                    rst.getDouble(9)       //estimated mileage cost

            );
            mileageTrackingDTOS.add(mileageTrackingDTO);
        }
        return mileageTrackingDTOS;
    }

    public ArrayList<String> getAllReservationIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Reservation");
        ArrayList<String> reservationId = new ArrayList<>();
        while (rst.next()) {
            reservationId.add(rst.getString(1));
        }
        return reservationId;
    }

    public boolean saveRecords(MileageTrackingDTO mileageTrackingDTO) throws SQLException {
        return  CrudUtil.execute(
                "insert into MileageTracking values (?,?,?,?,?,?,?,?,?)",
                mileageTrackingDTO.getId(),
                mileageTrackingDTO.getEstimatedMileage(),
                mileageTrackingDTO.getActualMileage(),
                mileageTrackingDTO.getExtraChargePerKm(),
                mileageTrackingDTO.getTotalExtraCharges(),
                mileageTrackingDTO.getReservationId(),
                mileageTrackingDTO.getStartDateMileage(),
                mileageTrackingDTO.getEndDateMileage(),
                mileageTrackingDTO.getEstimatedMileageCost()
        );
    }



    public boolean updateRecords(MileageTrackingDTO mileageTrackingDTO) throws SQLException {
        String query = "update MileageTracking set " +
                "estimatedMileage = ?, " +
                "actualMileage = ?, " +
                "extraChargesPerKm = ?, " +
                "totalExtraCharges = ?, " +
                "reservationId = ?, " +
                "startDateMileage = ?, " +
                "endDateMileage = ?, " +
                "estimatedMileageCost = ? " +
                "where id = ?";

        // Execute the update query with the provided values
        return CrudUtil.execute(query,
                mileageTrackingDTO.getEstimatedMileage(),
                mileageTrackingDTO.getActualMileage(),
                mileageTrackingDTO.getExtraChargePerKm(),
                mileageTrackingDTO.getTotalExtraCharges(),
                mileageTrackingDTO.getReservationId(),
                mileageTrackingDTO.getStartDateMileage(),
                mileageTrackingDTO.getEndDateMileage(),
                mileageTrackingDTO.getEstimatedMileageCost(),
                mileageTrackingDTO.getId()
        );
    }

    public boolean deleteRecords(String trackingId) throws SQLException {
        return CrudUtil.execute("delete from MileageTracking where id=?",trackingId );
    }
}
