package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaintenanceRecordDTO {
    private String id;
    private Date startDate;
    private Date endDate;
    private String description;
    private String maintenanceType;
    private String vehicleId;

}
