package Pegas.controller.rest;

import Pegas.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class RestController {
    private final SecurityService service;

    @GetMapping(value = "/{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> findAvatar(@PathVariable("id") Long id){
        byte[] bytes;
        try {
            bytes = service.findAvatar(id).orElseThrow();
        } catch (RuntimeException e){
            return new ResponseEntity<>(new ErrResponse(HttpStatus.NOT_FOUND.value(), "image is absent today"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }
}
