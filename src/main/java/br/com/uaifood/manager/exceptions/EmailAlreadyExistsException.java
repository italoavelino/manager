package br.com.uaifood.manager.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException() {
        super("Email já cadastrado");
    }
}
