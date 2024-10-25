package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {
    private String id;
    private String registrationNumber;
    private String make;
    private String model;
    private int year;
    private double mileage;
    private String status;
    private String vehicleTypeId;

}


