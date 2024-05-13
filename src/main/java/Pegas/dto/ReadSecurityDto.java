package Pegas.dto;

import Pegas.entity.Role;
import lombok.Value;

@Value
public class ReadSecurityDto {
    Long id;
    String email;
    Role role;
    String image;
}
