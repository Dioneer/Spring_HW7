package Pegas.mapper;

import Pegas.dto.CreateUpdateSecurityDto;
import Pegas.entity.Security;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class CreateUpdateMapper implements Mapper<CreateUpdateSecurityDto, Security>{
    @Override
    public Security map(CreateUpdateSecurityDto create) {
        Security security = new Security();
        security.setEmail(create.getEmail());
        security.setRole(create.getRole());
        Optional.ofNullable(create.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(i-> security.setImage(i.getOriginalFilename()));
        return security;
    }

    @Override
    public Security map(CreateUpdateSecurityDto update, Security security) {
        security.setEmail(update.getEmail());
        security.setRole(update.getRole());
        Optional.ofNullable(update.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(i-> security.setImage(i.getOriginalFilename()));
        return security;
    }
}
