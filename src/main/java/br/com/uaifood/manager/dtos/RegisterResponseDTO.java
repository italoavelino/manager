package br.com.uaifood.manager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterResponseDTO {
    private String token;
    private String message;
    private boolean success;
}
