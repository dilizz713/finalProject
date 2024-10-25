package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaintenanceRecord {
    private String id;
    private Date date;
    private String description;
    private String maintenanceType;
    private double cost;
    private String vehicleId;

}
