package br.com.uaifood.manager.controllers;

import br.com.uaifood.manager.domain.services.AuthService;
import br.com.uaifood.manager.dtos.request.ForgotPasswordRequestDTO;
import br.com.uaifood.manager.dtos.request.LoginRequestDTO;
import br.com.uaifood.manager.dtos.response.ForgotPasswordResponseDTO;
import br.com.uaifood.manager.dtos.response.LoginResponseDTO;
import br.com.uaifood.manager.dtos.request.RegisterRequestDTO;
import br.com.uaifood.manager.dtos.response.RegisterResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO response = authService.register(registerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        LoginResponseDTO response = authService.login(loginDTO);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponseDTO> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequestDTO forgotPasswordDTO
    ) {
        ForgotPasswordResponseDTO response = authService.forgotPassword(forgotPasswordDTO);

        return ResponseEntity.ok().body(response);
    }

}
