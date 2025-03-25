package br.com.uaifood.manager.controllers;

import br.com.uaifood.manager.dtos.LoginDTO;
import br.com.uaifood.manager.dtos.LoginResponseDTO;
import br.com.uaifood.manager.domain.services.AuthService;
import br.com.uaifood.manager.dtos.RegisterDTO;
import br.com.uaifood.manager.dtos.RegisterResponseDTO;
import br.com.uaifood.manager.exceptions.EmailAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            RegisterResponseDTO registerResponse = authService.register(registerDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
        } catch (EmailAlreadyExistsException e) {
            RegisterResponseDTO errorResponse = RegisterResponseDTO.builder()
                    .message("Este e-mail já está cadastrado")
                    .success(false)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            RegisterResponseDTO errorResponse = RegisterResponseDTO.builder()
                    .message("Ocorreu um erro inesperado")
                    .success(false)
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginresponse = authService.login(loginDTO);

        if (loginresponse.getToken() != null) {//colocar success no loginresponse
            return ResponseEntity.ok().body(
                    LoginResponseDTO.builder()
                            .token(loginresponse.getToken())
                            .message("Login realizado com sucesso")
                            .build()
            );
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                LoginResponseDTO.builder()
                        .message("Usuário ou senha inválidos")
                        .build()
        );
    }
}
