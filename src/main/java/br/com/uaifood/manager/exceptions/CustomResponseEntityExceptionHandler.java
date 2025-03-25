package br.com.uaifood.manager.exceptions;

import br.com.uaifood.manager.dtos.ErrorMessageDTO;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public CustomResponseEntityExceptionHandler(MessageSource message) {
        this.messageSource = message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorMessageDTO> errors = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());

            ErrorMessageDTO error = ErrorMessageDTO.builder()
                    .field(err.getField())
                    .message(message)
                    .build();

            errors.add(error);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}