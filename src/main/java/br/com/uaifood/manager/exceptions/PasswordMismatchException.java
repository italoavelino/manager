package br.com.uaifood.manager.exceptions;

import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends CustomException {
    public PasswordMismatchException() {
        super("Senha e confirmação não coincidem", HttpStatus.BAD_REQUEST, "PASSWORD_MISMATCH");
    }
}
