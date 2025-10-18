package br.com.uaifood.manager.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {
        super("Token inválido", HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
    }
}