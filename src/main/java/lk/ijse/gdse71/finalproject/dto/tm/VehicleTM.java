package lk.ijse.gdse71.finalproject.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleTM {
    private String id;
    private String registrationNumber;
    private String make;
    private String model;
    private int year;
    private double mileage;
    private String status;
    private String vehicleType;


}
