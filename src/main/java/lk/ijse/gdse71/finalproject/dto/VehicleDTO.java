package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDTO {
    private String id;
    private String registrationNumber;
    private String make;
    private String model;
    private int year;
    private double mileage;
    private String status;
    private String vehicleType;



}


