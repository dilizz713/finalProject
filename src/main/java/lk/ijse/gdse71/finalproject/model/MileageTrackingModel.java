package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
                    rst.getDouble(9),       //estimated mileage cost
                    rst.getDate(10).toLocalDate(),       //start date
                    rst.getDate(11).toLocalDate()       //end date

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
                "insert into MileageTracking values (?,?,?,?,?,?,?,?,?,?,?)",
                mileageTrackingDTO.getId(),
                mileageTrackingDTO.getEstimatedMileage(),
                mileageTrackingDTO.getActualMileage(),
                mileageTrackingDTO.getExtraChargePerKm(),
                mileageTrackingDTO.getTotalExtraCharges(),
                mileageTrackingDTO.getReservationId(),
                mileageTrackingDTO.getStartDateMileage(),
                mileageTrackingDTO.getEndDateMileage(),
                mileageTrackingDTO.getEstimatedMileageCost(),
                mileageTrackingDTO.getStartDate(),
                mileageTrackingDTO.getEndDate()
        );
    }



    public boolean updateRecords(MileageTrackingDTO mileageTrackingDTO) throws SQLException {
       return CrudUtil.execute(" update MileageTracking set estimatedMileage=? , actualMileage=? , extraChargesPerKm=?, totalExtraCharges=?, reservationId=? , startDateMileage=?, endDateMileage=?, estimatedMileageCost=?, startDate=?,endDate=? where id=?",
                mileageTrackingDTO.getEstimatedMileage(),
                mileageTrackingDTO.getActualMileage(),
                mileageTrackingDTO.getExtraChargePerKm(),
                mileageTrackingDTO.getTotalExtraCharges(),
                mileageTrackingDTO.getReservationId(),
                mileageTrackingDTO.getStartDateMileage(),
                mileageTrackingDTO.getEndDateMileage(),
                mileageTrackingDTO.getEstimatedMileageCost(),
                mileageTrackingDTO.getStartDate(),
                mileageTrackingDTO.getEndDate(),
                mileageTrackingDTO.getId()
        );
    }

    public boolean deleteRecords(String trackingId) throws SQLException {
        return CrudUtil.execute("delete from MileageTracking where id=?",trackingId );
    }

    public MileageTrackingDTO getMileageTrackingByReservationId(String reservationId) throws SQLException {
        String query = "select * from MileageTracking where reservationId = ?";

        ResultSet resultSet = CrudUtil.execute(query,reservationId);

            if (resultSet.next()) {

                String trackingId = resultSet.getString("tracking_id");
                double startDateMileage = resultSet.getDouble("start_date_mileage");
                double endDateMileage = resultSet.getDouble("end_date_mileage");
                double estimatedMileage = resultSet.getDouble("estimated_mileage");
                double actualMileage = resultSet.getDouble("actual_mileage");
                double estimatedMileageCost = resultSet.getDouble("estimated_mileage_cost");
                double extraChargePerKm = resultSet.getDouble("extra_charge_per_km");
                double totalExtraCharges = resultSet.getDouble("total_extra_charges");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endDate = resultSet.getDate("end_date").toLocalDate();

                return new MileageTrackingDTO(
                        trackingId,
                        estimatedMileage,
                        actualMileage,
                        extraChargePerKm,
                        totalExtraCharges,
                        reservationId,
                        startDateMileage,
                        endDateMileage,
                        estimatedMileageCost,
                        startDate,
                        endDate
                );
            }

        return null;
    }

    public ArrayList<MileageTrackingDTO> getRecordsBySearch(String keyword) throws SQLException {
        String searchQuery = "select * from MileageTracking where id like ? or reservationId like ? or startDate like ? or endDate like ? ";

        ResultSet rst = CrudUtil.execute(searchQuery,  "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");


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
                    rst.getDouble(9),       //estimated mileage cost
                    rst.getDate(10).toLocalDate(),       //start date
                    rst.getDate(11).toLocalDate()       //end date

            );
            mileageTrackingDTOS.add(mileageTrackingDTO);
        }
        return mileageTrackingDTOS;
    }
}
