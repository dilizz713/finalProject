package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
    private String id;
    private double amount;
    private Date date;
    private String type;
    private String status;
    private String reservationId;

}
