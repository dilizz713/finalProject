package lk.ijse.gdse71.finalproject.view.tdm;

import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationTM {
    private String id;
    private LocalDate reservationDate;
    private String customerName;
    private String numberPlate;
    private String model;
    private double price;
    private String status;
    private Button updateButton;
    private Button addMileageButton;


}
