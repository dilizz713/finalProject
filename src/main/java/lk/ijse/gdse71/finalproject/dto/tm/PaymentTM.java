package lk.ijse.gdse71.finalproject.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

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
