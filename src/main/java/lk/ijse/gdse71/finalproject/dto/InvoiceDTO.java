package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceDTO {
        private String id;
        private  Date date;
        private double totalAmount;
        private String status;
        private String reservationId;
        private String customerId;

}
