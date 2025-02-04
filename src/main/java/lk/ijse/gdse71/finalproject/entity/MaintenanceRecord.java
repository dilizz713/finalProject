package lk.ijse.gdse71.finalproject.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaintenanceRecord {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String vehicleId;
    private String status;

}
