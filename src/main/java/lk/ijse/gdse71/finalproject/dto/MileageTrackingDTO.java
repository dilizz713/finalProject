package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MileageTrackingDTO {
    private String id;
    private double estimatedMileage;
    private double actualMileage;
    private double extraChargePerKm;
    private double totalExtraCharges;
    private String reservationId;

}