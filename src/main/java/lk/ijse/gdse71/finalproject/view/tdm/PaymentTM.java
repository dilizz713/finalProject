package lk.ijse.gdse71.finalproject.view.tdm;

import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentTM {
    private String id;
    private String customer;
    private double advancePayment;
    private double fullPayment;
    private String reservationId;
    private LocalDate date;
    private String status;
    private Button updateButton;


}
