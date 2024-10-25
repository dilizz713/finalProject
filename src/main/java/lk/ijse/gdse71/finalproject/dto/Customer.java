package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private String id;
    private String name;
    private String address;
    private String email;
    private int phoneNumber;
    private String nic;
}
