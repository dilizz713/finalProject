package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private String email;
    private int phoneNumber;
    private String nic;




    public CustomerDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CustomerDTO(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
