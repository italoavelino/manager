package br.com.uaifood.manager.exceptions;

import org.springframework.http.HttpStatus;

public class WeakPasswordException extends CustomException {
    public WeakPasswordException() {
        super("Senha muito fraca", HttpStatus.UNPROCESSABLE_ENTITY, "WEAK_PASSWORD");
    }
}