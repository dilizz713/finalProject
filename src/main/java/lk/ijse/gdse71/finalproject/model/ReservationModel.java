package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

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
            LocalDate reservationDate = rst.getDate("reservationDate") != null
                    ? rst.getDate("reservationDate").toLocalDate()
                    : null;


            reservationDTOS.add(new ReservationDTO(
                    rst.getString(1),                       //reservation id
                    rst.getString(2),                       //customer id
                    rst.getString(3),                       //vehicle id
                    rst.getString(4),                       //status
                    reservationDate
            ));

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

    public ArrayList<String> getAllVehicleIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Vehicle");
        ArrayList<String> vehilceId = new ArrayList<>();
        while (rst.next()) {
            vehilceId.add(rst.getString(1));
        }
        return vehilceId;
    }

    public String getCustomerNameById(String customerId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select name from Customer where id=?",customerId);
        return rst.next() ? rst.getString(1) : null;
    }

    public String getVehiclePriceById(String vehicleID) throws SQLException {
        ResultSet rst = CrudUtil.execute("select price from Vehicle where id = ?", vehicleID);
        return rst.next() ? rst.getString(1) : null;
    }


    public String getVehicleNameById(String vehicleId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select model from Vehicle where id = ?", vehicleId);
        return rst.next() ? rst.getString(1) : null;
    }


    public boolean saveReservation(ReservationDTO reservationDTO) throws SQLException {
       return CrudUtil.execute(
                "insert into Reservation values (?,?,?,?,?)",
                reservationDTO.getId(),
                reservationDTO.getCustomerId(),
                reservationDTO.getVehicleId(),
                reservationDTO.getStatus(),
                reservationDTO.getReservationDate()
        );

    }


    public boolean updateReservation(ReservationDTO reservationDTO) throws SQLException {
        return CrudUtil.execute(
                "update  Reservation set customerId=?, vehicleId=?,  status=?, reservationDate=? where id=?",
                reservationDTO.getCustomerId(),
                reservationDTO.getVehicleId(),
                reservationDTO.getStatus(),
                reservationDTO.getReservationDate(),
                reservationDTO.getId()
        );
    }

    public boolean deleteReservation(String reservationId) throws SQLException {
        return CrudUtil.execute("delete from Reservation where id=?",reservationId );
    }

    public ArrayList<ReservationDTO> getReservationsBySearch(String keyword) throws SQLException {
        String searchQuery = "select * from Reservation where id like ? or vehicleId like ? or customerId like ? or reservationDate like ? or status like ?";

        ResultSet rst = CrudUtil.execute(searchQuery,  "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%","%" + keyword + "%", "%" + keyword + "%");


        ArrayList<ReservationDTO> reservationDTOS = new ArrayList<>();

        while (rst.next()) {
            ReservationDTO reservationDTO = new ReservationDTO(
                    rst.getString(1),                       //reservation id
                    rst.getString(2),                       //customer id
                    rst.getString(3),                       //vehicle id
                    rst.getString(4),                       //status
                    rst.getDate(5).toLocalDate()             //reservation date

            );
            reservationDTOS.add(reservationDTO);
        }
        return reservationDTOS;
    }

    public ArrayList<CustomerDTO> getCustomerDTOsForReservation() throws SQLException {
        ArrayList<CustomerDTO> customers = new ArrayList<>();

        String sql = "select id,name from Customer";

        try (ResultSet rst = CrudUtil.execute(sql)) {
            while (rst.next()) {
               customers.add(new CustomerDTO(
                       rst.getString("id"),
                       rst.getString("name")       // name
               ));
            }
        }

        return customers;
    }




    public String getNumberPlateById(String vehicleId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select numberPlate from Vehicle where id = ?", vehicleId);
        return rst.next() ? rst.getString(1) : null;
    }


    public String getAllVehicleDetails(String vehicleId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select model,numberPlate,price from Vehicle where id = ?", vehicleId);
        return rst.next() ? rst.getString(1) : null;
    }

    public ReservationDTO getReservationById(String reservationId) throws SQLException {
        String query = "select * from Reservation where id=?";
        ResultSet rst = CrudUtil.execute(query,reservationId);

        if(rst.next()){
            String id = rst.getString("id");
            String customerId = rst.getString("customerId");
            String vehicleId = rst.getString("vehicleId");
            String status = rst.getString("status");
            LocalDate reservationDate = rst.getDate("reservationDate").toLocalDate();

            return new ReservationDTO(id,customerId,vehicleId,status,reservationDate);

        }
        return null;
    }


    public ReservationDTO getReservationDetails(String reservationId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select status from Reservation where id=?", reservationId);
        if(rst.next()){
            return  new ReservationDTO(
                    reservationId,
                    rst.getString("status")


            );
        }
        return null;
    }

    public String getVehiclePrice(String reservationId) throws SQLException {
        ResultSet rst = CrudUtil.execute(
                """
                select v.price
                from Reservation r
                join Vehicle v
                on r.vehicleId = v.id where r.id = ?
                """, reservationId
        );
        if (rst.next()) {
            return rst.getString("price");
        }
        return reservationId;
    }

    public ArrayList<String> getAllReservationIds() throws SQLException {
        String query = "select id from Reservation";
        ResultSet rst = CrudUtil.execute(query);

        ArrayList<String> reservationID = new ArrayList<>();
        while (rst.next()){
            reservationID.add(rst.getString(1));
        }
        return reservationID;
    }

    public String getCustomerNameByReservationId(String reservationId) throws SQLException {
        String query = "select c.name from Customer c inner join Reservation r on c.id = r.customerId where r.id = ?";
        ResultSet resultSet = CrudUtil.execute(query, reservationId);

        return resultSet.next() ? resultSet.getString(1) : null;
    }

    public String getVehicleIdByReservationId(String reservationId) throws SQLException {
        String query = "select vehicleId from Reservation where id = ?";

        ResultSet rst = CrudUtil.execute(query,reservationId);
        if (rst.next()) {
             return rst.getString("vehicleId");
        }

        return null;
    }

    public double getEstimatedMileageCost(String reservationId) throws SQLException {
        String query = "select estimatedMileageCost from MileageTracking where reservationId=?";

        ResultSet resultSet = CrudUtil.execute(query, reservationId);

        if (resultSet.next()) {
            return resultSet.getDouble("estimatedMileageCost");
        }
        return 0.0;
    }

    public double getTotalExtraCharges(String reservationId) throws SQLException {
        String query = "select totalExtraCharges from MileageTracking where reservationId=?";

        ResultSet resultSet = CrudUtil.execute(query, reservationId);

        if (resultSet.next()) {
            return resultSet.getDouble("totalExtraCharges");
        }
        return 0.0;
    }

    public double getRepairCostByVehicleId(String vehicleId) throws SQLException {
        String query = "select repairCost from VehicleDamage where vehicleId = ?";
        ResultSet resultSet = CrudUtil.execute(query, vehicleId);

        if (resultSet.next()) {
            return resultSet.getDouble("repairCost");
        }
        return 0.0;
    }


    public double getEndMileageForReservation(String reservationId) throws SQLException {
        String query = "select endDateMileage from MileageTracking where reservationId=?";
        ResultSet rst = CrudUtil.execute(query,reservationId);

        if(rst.next()){
            return rst.getDouble("endDateMileage");
        }
        return 0;
    }

    public String getPaymentIdByReservation(String reservationId) throws SQLException {
        String query = "select id from Payment where reservationId=?";
        ResultSet rst = CrudUtil.execute(query,reservationId);

        if(rst.next()){
            return rst.getString("id");
        }
        return null;
    }

    public MileageTrackingDTO getMileageDetails(String reservationId) throws SQLException {
        String query = "SELECT estimatedMileage, actualMileage, extraChargesPerKm, totalExtraCharges, estimatedMileageCost FROM MileageTracking WHERE reservationId = ?" ;

        ResultSet rst = CrudUtil.execute(query, reservationId);

        if (rst.next()) {
            return new MileageTrackingDTO(
                    rst.getDouble("estimatedMileage"),
                    rst.getDouble("actualMileage"),
                    rst.getDouble("extraChargesPerKm"),
                    rst.getDouble("totalExtraCharges"),
                    rst.getDouble("estimatedMileageCost")

            );
        }
        return null;
    }

    public CustomerDTO getCustomerDetailsByReservationId(String reservationId) throws SQLException {
        String sql = """
            SELECT c.id, c.name, c.email
            FROM Customer c
            JOIN Reservation r ON c.id = r.customerId
            WHERE r.id = ?;
        """;
        try {
            ResultSet rst = CrudUtil.execute(sql, reservationId);
            if (rst.next()) {
                return new CustomerDTO(
                        rst.getString("id"),
                        rst.getString("name"),
                        rst.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customer details: " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }
}
