package Pegas.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.path}")
    private String pathSave;

    @SneakyThrows
    public void upload(String imagePath, InputStream content){
        Path fullPath = Path.of(pathSave, imagePath);

        try(content){
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String path){
        Path fullPath = Path.of(pathSave, path);
        return Files.exists(fullPath) ? Optional.of(Files.readAllBytes(fullPath)) : Optional.empty();
    }

}
