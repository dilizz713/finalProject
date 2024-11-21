package lk.ijse.gdse71.finalproject.dto.tm;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDamageTM {
    private String id;
    private String description;
    private LocalDate reportedDate;
    private double repairCost;
    private String vehicleId;
    private String model;
    private String customerId;
    private String customerName;

}
