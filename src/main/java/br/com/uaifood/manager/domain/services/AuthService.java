package br.com.uaifood.manager.domain.services;

import br.com.uaifood.manager.dtos.LoginDTO;
import br.com.uaifood.manager.dtos.LoginResponseDTO;
import br.com.uaifood.manager.dtos.RegisterDTO;
import br.com.uaifood.manager.dtos.RegisterResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public RegisterResponseDTO register(RegisterDTO registerDTO);
    public LoginResponseDTO login(LoginDTO loginDTO);
}
