package lk.ijse.gdse71.finalproject.dto.tm;

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
    private String type;
    private double amount;
    private String reservationId;
    private LocalDate date;
    private String status;


}
