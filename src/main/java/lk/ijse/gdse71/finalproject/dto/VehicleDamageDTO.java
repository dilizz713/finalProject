package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDamageDTO {
    private String id;
    private String description;
    private Date reportedDate;
    private double repairCost;
    private String vehicleId;

}
