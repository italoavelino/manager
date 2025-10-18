package br.com.uaifood.manager.domain.services;

import br.com.uaifood.manager.dtos.request.ForgotPasswordRequestDTO;
import br.com.uaifood.manager.dtos.request.LoginRequestDTO;
import br.com.uaifood.manager.dtos.request.RegisterRequestDTO;
import br.com.uaifood.manager.dtos.response.ForgotPasswordResponseDTO;
import br.com.uaifood.manager.dtos.response.LoginResponseDTO;
import br.com.uaifood.manager.dtos.response.RegisterResponseDTO;

public interface AuthService {
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);

    public ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO);
}
