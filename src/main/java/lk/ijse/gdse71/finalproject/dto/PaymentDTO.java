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
    private double amount;
    private LocalDate date;
    private String type;
    private String status;
    private String reservationId;

}
