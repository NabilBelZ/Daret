package org.gestion.daret.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDto {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;

}
