package Pegas.service;

import Pegas.dto.CreateUpdateSecurityDto;
import Pegas.dto.ReadSecurityDto;
import Pegas.entity.Security;
import Pegas.mapper.CreateUpdateMapper;
import Pegas.mapper.ReadMapper;
import Pegas.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {
    private final CreateUpdateMapper createUpdateMapper;
    private final ReadMapper readMapper;
    private final SecurityRepository repository;
    private final ImageService imageService;

    public ReadSecurityDto create(CreateUpdateSecurityDto create){
        return Optional.of(create)
                .map(i-> {
                    uploadImage(i.getImage());
                    return createUpdateMapper.map(i);
                })
                .map(repository::save)
                .map(readMapper::map)
                .orElseThrow();
    }

    public ReadSecurityDto update(CreateUpdateSecurityDto update){
        return Optional.of(update)
                .map(i-> {
                    uploadImage(i.getImage());
                    return createUpdateMapper.map(i, new Security());
                })
                .map(repository::save)
                .map(readMapper::map)
                .orElseThrow();
    }

    public Optional<ReadSecurityDto> findById(Long id){
        return repository.findById(id).map(readMapper::map);
    }

    @SneakyThrows
    public Optional<byte[]> findAvatar(Long id){
        return repository.findById(id).map(Security::getImage).filter(StringUtils::hasText).flatMap(imageService::get);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if(!image.isEmpty()){
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return repository.findByEmail(userEmail).map(i-> {
            return new User(
                    i.getEmail(), i.getPassword(), Collections.singleton(i.getRole()));
        }).orElseThrow(()-> new UsernameNotFoundException("Failed to retrieve user: " + userEmail));
    }
}
