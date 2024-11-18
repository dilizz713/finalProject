package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaintenanceRecordDTO {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String vehicleId;
    private String status;

}
