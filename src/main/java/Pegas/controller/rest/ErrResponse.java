package Pegas.controller.rest;

import lombok.Data;

@Data
public class ErrResponse {
    private final int code;
    private final String description;
}
