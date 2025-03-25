package br.com.uaifood.manager.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("Usuário não encontrado");
    }
}
