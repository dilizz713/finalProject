package lk.ijse.gdse71.finalproject.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
    private String id;
    private LocalDate date;
    private String status;
    private String reservationId;
    private double advancePayment;
    private double fullPayment;

}
