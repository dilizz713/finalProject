package lk.ijse.gdse71.finalproject.view.tdm;

import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaintenanceRecordTM {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String vehicleId;
    private String model;
    private String description;
    private String status;
    private Button updateButton;

}
