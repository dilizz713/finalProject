package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDTO {
    private String id;
    private LocalDate date;
    private String status;
    private String reservationId;
    private double advancePayment;
    private double fullPayment;

}
