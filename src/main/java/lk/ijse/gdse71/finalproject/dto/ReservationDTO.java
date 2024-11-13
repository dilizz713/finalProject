package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationDTO {
    private String id;
    private Date startDate;
    private Date endDate;
    private double estimatedMileage;
    private String customerId;
    private String vehicleId;
    private String driverId;
    private String status;

}
