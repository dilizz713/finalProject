package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationDTO {
    private String id;
    private String customerId;
    private String vehicleId;
    private String status;
    private LocalDate reservationDate;

    public ReservationDTO(String id,String status) {
        this.id = id;
        this.status = status;
    }

    public ReservationDTO(String id, double price) {
        this.id = id;

    }



}
