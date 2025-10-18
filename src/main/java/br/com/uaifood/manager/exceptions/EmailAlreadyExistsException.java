package br.com.uaifood.manager.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends CustomException {
    public EmailAlreadyExistsException() {
        super("E-mail já cadastrado", HttpStatus.CONFLICT, "EMAIL_ALREADY_EXISTS");
    }
}