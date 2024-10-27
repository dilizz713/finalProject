package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleTypeDTO {
    private String id;
    private String typeName;
    private String description;
    private double baseRate;
    private int nos;

}
