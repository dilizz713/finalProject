package lk.ijse.gdse71.finalproject.view.tdm;

import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleTM {
    private String id;
    private String numberPlate;
    private String make;
    private String model;
    private String vehicleType;
    private LocalDate date;
    private double price;
    private byte[] image;
    private Button updateButton;





}
