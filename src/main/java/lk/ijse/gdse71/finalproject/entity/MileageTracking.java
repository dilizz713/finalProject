package lk.ijse.gdse71.finalproject.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MileageTracking {
    private String id;
    private double estimatedMileage;
    private double actualMileage;
    private double extraChargePerKm;
    private double totalExtraCharges;
    private String reservationId;
    private double startDateMileage;
    private double endDateMileage;
    private double estimatedMileageCost;
    private LocalDate startDate;
    private LocalDate endDate;


    public MileageTracking(double estimatedMileage, double actualMileage, double extraChargePerKm, double totalExtraCharges, double estimatedMileageCost) {
        this.estimatedMileage = estimatedMileage;
        this.actualMileage = actualMileage;
        this.extraChargePerKm = extraChargePerKm;
        this.totalExtraCharges = totalExtraCharges;
        this.estimatedMileageCost = estimatedMileageCost;
    }
}
