package Pegas.dto;

import Pegas.entity.Role;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class CreateUpdateSecurityDto {
    String email;
    Role role;
    MultipartFile image;
}
