package br.com.uaifood.manager.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends CustomException {
  public InvalidCredentialsException(String customMessage) {
    super(customMessage, HttpStatus.UNAUTHORIZED, "AUTH_FAILED");
  }
}

