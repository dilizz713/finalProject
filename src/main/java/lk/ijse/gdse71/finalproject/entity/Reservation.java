package lk.ijse.gdse71.finalproject.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
    private String id;
    private String customerId;
    private String vehicleId;
    private String status;
    private LocalDate reservationDate;

    public Reservation(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public Reservation(String id, double price) {
        this.id = id;

    }


}
