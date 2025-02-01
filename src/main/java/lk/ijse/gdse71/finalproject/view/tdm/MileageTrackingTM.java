package lk.ijse.gdse71.finalproject.view.tdm;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MileageTrackingTM {
    private String trackingId;
    private String reservationId;
    private LocalDate startDate;
    private double startDateMileage;
    private LocalDate endDate;
    private double endDateMileage;
    private double estimatedMileage;
    private double actualMileage;
    private double estimatedMileageCost;
    private double extraChargePerKm;
    private double totalExtraCharges;

}