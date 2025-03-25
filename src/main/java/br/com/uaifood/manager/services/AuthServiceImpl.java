package br.com.uaifood.manager.services;

import br.com.uaifood.manager.configurations.JwtUtil;
import br.com.uaifood.manager.domain.model.User;
import br.com.uaifood.manager.domain.repositories.UserRepository;
import br.com.uaifood.manager.dtos.LoginDTO;
import br.com.uaifood.manager.dtos.LoginResponseDTO;
import br.com.uaifood.manager.dtos.RegisterDTO;
import br.com.uaifood.manager.domain.services.AuthService;
import br.com.uaifood.manager.dtos.RegisterResponseDTO;
import br.com.uaifood.manager.exceptions.EmailAlreadyExistsException;
import br.com.uaifood.manager.exceptions.UserNotFound;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl (
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResponseDTO register(RegisterDTO registerDTO){
        userRepository.findByEmail(registerDTO.getEmail())
                .ifPresent((user) -> {
                    throw new EmailAlreadyExistsException();
                });

        var encodedPassword = passwordEncoder.encode(registerDTO.getPassword());

        User user = User.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(encodedPassword)
                .build();

        User registeredUser = userRepository.save(user);

        String token = jwtUtil.generateToken(registeredUser.getId());

        return RegisterResponseDTO.builder()
                .message("Usuário criado com sucesso")
                .token(token)
                .success(true)
                .build();
    };

    public LoginResponseDTO login(LoginDTO loginDTO){
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UserNotFound());
        return LoginResponseDTO.builder().build();
    };
}
