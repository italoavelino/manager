package br.com.uaifood.manager.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
    private String code;
    private String message;
    private int status;
}