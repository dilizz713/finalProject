package lk.ijse.gdse71.finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerTM {
    private String id;
    private String name;
    private String address;
    private String email;
    private int phoneNumber;
    private String nic;
}
