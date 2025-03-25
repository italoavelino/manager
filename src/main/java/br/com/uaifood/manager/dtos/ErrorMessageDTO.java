package br.com.uaifood.manager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorMessageDTO {
    private String field;
    private String message;
}