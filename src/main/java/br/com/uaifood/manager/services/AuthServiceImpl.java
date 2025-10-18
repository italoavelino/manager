package br.com.uaifood.manager.services;

import br.com.uaifood.manager.configurations.JwtUtil;
import br.com.uaifood.manager.domain.model.PasswordResetToken;
import br.com.uaifood.manager.domain.model.User;
import br.com.uaifood.manager.domain.repositories.PasswordResetTokenRepository;
import br.com.uaifood.manager.domain.repositories.UserRepository;
import br.com.uaifood.manager.domain.services.AuthService;
import br.com.uaifood.manager.dtos.request.ForgotPasswordRequestDTO;
import br.com.uaifood.manager.dtos.request.LoginRequestDTO;
import br.com.uaifood.manager.dtos.response.ForgotPasswordResponseDTO;
import br.com.uaifood.manager.dtos.response.LoginResponseDTO;
import br.com.uaifood.manager.dtos.request.RegisterRequestDTO;
import br.com.uaifood.manager.dtos.response.RegisterResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserValidationService userValidationService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordResetService passwordResetService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            UserValidationService userValidationService,
            PasswordResetTokenRepository passwordResetTokenRepository,
            PasswordResetService passwordResetService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userValidationService = userValidationService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordResetService = passwordResetService;
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerDTO) {
        userValidationService.validateRegistrationData(
                registerDTO.getEmail(),
                registerDTO.getPassword(),
                registerDTO.getConfirmPassword()
        );

        User user = User.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail().toLowerCase())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .enabled(true)
                .build();

        User registeredUser = userRepository.save(user);

        String token = jwtUtil.generateToken(registeredUser);

        return RegisterResponseDTO.builder()
                .email(user.getEmail())
                .message("Usuário registrado com sucesso")
                .token(token)
                .build();
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userValidationService.validateLoginCredentials(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        String token = jwtUtil.generateToken(user);

        return LoginResponseDTO.builder()
                .token(token)
                .build();
    }


    public ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        User user = userValidationService.findUserByEmailOrThrow(
                forgotPasswordRequestDTO.getEmail(),
                "Email não encontrado"
        );

        passwordResetTokenRepository.deleteByUserId(user.getId());

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .userId(user.getId())
                .token(token)
                .expiryDate(passwordResetService.calculateExpiryDate(24 * 60))
                .build();

        passwordResetTokenRepository.save(resetToken);

        passwordResetService.sendPasswordResetEmail(user, token);

        return ForgotPasswordResponseDTO.builder()
                .message("Email de recuperação enviado para " + user.getEmail())
                .success(true)
                .build();
    }
}
