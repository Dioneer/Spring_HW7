package Pegas.mapper;

import Pegas.dto.ReadSecurityDto;
import Pegas.entity.Security;
import org.springframework.stereotype.Component;

@Component
public class ReadMapper implements Mapper<Security, ReadSecurityDto>{
    @Override
    public ReadSecurityDto map(Security security) {
        return new ReadSecurityDto(security.getId(), security.getEmail(), security.getRole(),
                security.getImage(), security.getPassword());
    }
}
