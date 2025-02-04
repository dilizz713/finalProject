package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDTO {
    private String id;
    private String make;
    private String model;
    private String vehicleType;
    private byte[] image;
    private String numberPlate;
    private double price;
    private LocalDate registrationDate;


}


