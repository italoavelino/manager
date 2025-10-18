package br.com.uaifood.manager.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDTO {

    private String email;
    private String message;
    private String token;
}