package lk.ijse.gdse71.finalproject.dto.tm;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationTM {
    private String id;
    private Date startDate;
    private Date endDate;
    private double estimatedMileage;
    private String customerId;
    private String customerName;
    private String vehicleId;
    private String model;
    private String driverId;
    private String driverName;
    private String status;

}
