package lk.ijse.gdse71.finalproject.dto.tm;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaintenanceRecordTM {
    private String id;
    private Date startDate;
    private Date endDate;
    private String maintenanceType;
    private String vehicleId;
    private String model;
    private String description;

}
