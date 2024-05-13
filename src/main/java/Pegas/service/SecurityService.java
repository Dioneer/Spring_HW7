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
import java.util.List;
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

    public Optional<ReadSecurityDto> update(Long id, CreateUpdateSecurityDto update){
        return repository.findById(id)
                .map(i-> {
                    uploadImage(update.getImage());
                    return createUpdateMapper.map(update, i);
                })
                .map(repository::save)
                .map(readMapper::map);
    }

    public Optional<ReadSecurityDto> findById(Long id){
        return repository.findById(id).map(readMapper::map);
    }

    public boolean delete(Long id){
        return repository.findById(id).map(i->{
            repository.delete(i);
            repository.flush();
            return true;
        }).orElse(false);
    }

    public List<ReadSecurityDto> findAll(){
        return repository.findAll().stream().map(readMapper::map).toList();
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
        return repository.findByEmail(userEmail).map(i-> new User(
                i.getEmail(), i.getPassword(), Collections.singleton(i.getRole())))
                .orElseThrow(()-> new UsernameNotFoundException("Failed to retrieve user: " + userEmail));
    }
}
