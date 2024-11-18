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
    private LocalDate startDate;
    private LocalDate endDate;
    private String customerId;
    private String vehicleId;
    private String status;
    private LocalDate reservationDate;

    public ReservationDTO(String id, LocalDate startDate, LocalDate endDate , String status) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public ReservationDTO(String id, double price) {
        this.id = id;

    }


}
