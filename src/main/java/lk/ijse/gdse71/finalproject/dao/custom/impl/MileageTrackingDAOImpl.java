package lk.ijse.gdse71.finalproject.dao.custom.impl;

import lk.ijse.gdse71.finalproject.dao.custom.MileageTrackingDAO;
import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.entity.MileageTracking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MileageTrackingDAOImpl implements MileageTrackingDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select id from MileageTracking order by id desc limit 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i + 1;
            return String.format("M%03d", newId);
        }
        return "M001";
    }
    @Override
    public ArrayList<MileageTracking> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from MileageTracking");

        ArrayList<MileageTracking> mileageTrackingDTOS = new ArrayList<>();

        while (rst.next()) {
            MileageTracking entity = new MileageTracking(
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
            mileageTrackingDTOS.add(entity);
        }
        return mileageTrackingDTOS;
    }

    @Override
    public boolean save(MileageTracking entity) throws SQLException {
        return SQLUtil.execute(
                "insert into MileageTracking values (?,?,?,?,?,?,?,?,?,?,?)",
                entity.getId(),
                entity.getEstimatedMileage(),
                entity.getActualMileage(),
                entity.getExtraChargePerKm(),
                entity.getTotalExtraCharges(),
                entity.getReservationId(),
                entity.getStartDateMileage(),
                entity.getEndDateMileage(),
                entity.getEstimatedMileageCost(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }

    @Override
    public boolean update(MileageTracking entity) throws SQLException {
        return SQLUtil.execute(" update MileageTracking set estimatedMileage=? , actualMileage=? , extraChargesPerKm=?, totalExtraCharges=?, reservationId=? , startDateMileage=?, endDateMileage=?, estimatedMileageCost=?, startDate=?,endDate=? where id=?",
                entity.getEstimatedMileage(),
                entity.getActualMileage(),
                entity.getExtraChargePerKm(),
                entity.getTotalExtraCharges(),
                entity.getReservationId(),
                entity.getStartDateMileage(),
                entity.getEndDateMileage(),
                entity.getEstimatedMileageCost(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getId()
        );
    }
    @Override
    public boolean delete(String trackingId) throws SQLException {
        return SQLUtil.execute("delete from MileageTracking where id=?", trackingId);
    }

    public MileageTrackingDTO getMileageTrackingByReservationId(String reservationId) throws SQLException {
        String query = "select * from MileageTracking where reservationId = ?";

        ResultSet resultSet = SQLUtil.execute(query, reservationId);

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
    @Override
    public ArrayList<MileageTracking> search(String keyword) throws SQLException {
        String searchQuery = "select * from MileageTracking where id like ? or reservationId like ? or startDate like ? or endDate like ? ";

        ResultSet rst = SQLUtil.execute(searchQuery, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");


        ArrayList<MileageTracking> mileageTrackingDTOS = new ArrayList<>();

        while (rst.next()) {
            MileageTracking entity = new MileageTracking(
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
            mileageTrackingDTOS.add(entity);
        }
        return mileageTrackingDTOS;
    }
    @Override
    public double getEstimatedMileageCost(String reservationId) throws SQLException {
        String query = "select estimatedMileageCost from MileageTracking where reservationId=?";

        ResultSet resultSet = SQLUtil.execute(query, reservationId);

        if (resultSet.next()) {
            return resultSet.getDouble("estimatedMileageCost");
        }
        return 0.0;
    }
    @Override
    public double getTotalExtraCharges(String reservationId) throws SQLException {
        String query = "select totalExtraCharges from MileageTracking where reservationId=?";

        ResultSet resultSet = SQLUtil.execute(query, reservationId);

        if (resultSet.next()) {
            return resultSet.getDouble("totalExtraCharges");
        }
        return 0.0;
    }
    @Override
    public double getEndMileageForReservation(String reservationId) throws SQLException {
        String query = "select endDateMileage from MileageTracking where reservationId=?";
        ResultSet rst = SQLUtil.execute(query, reservationId);

        if (rst.next()) {
            return rst.getDouble("endDateMileage");
        }
        return 0;
    }
    @Override
    public MileageTracking getMileageDetails(String reservationId) throws SQLException {
        String query = "SELECT estimatedMileage, actualMileage, extraChargesPerKm, totalExtraCharges, estimatedMileageCost FROM MileageTracking WHERE reservationId = ?";

        ResultSet rst = SQLUtil.execute(query, reservationId);

        if (rst.next()) {
            return new MileageTracking(
                    rst.getDouble("estimatedMileage"),
                    rst.getDouble("actualMileage"),
                    rst.getDouble("extraChargesPerKm"),
                    rst.getDouble("totalExtraCharges"),
                    rst.getDouble("estimatedMileageCost")

            );
        }
        return null;
    }
}
