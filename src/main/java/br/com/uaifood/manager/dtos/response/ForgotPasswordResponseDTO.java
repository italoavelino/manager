package br.com.uaifood.manager.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForgotPasswordResponseDTO {
    private String message;
    private boolean success;
}
