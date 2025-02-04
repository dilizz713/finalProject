package lk.ijse.gdse71.finalproject.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDamage {
    private String id;
    private String description;
    private LocalDate reportedDate;
    private double repairCost;
    private String vehicleId;

}
