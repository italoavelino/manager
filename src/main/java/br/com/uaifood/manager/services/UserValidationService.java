package br.com.uaifood.manager.services;

import br.com.uaifood.manager.domain.model.User;
import br.com.uaifood.manager.domain.repositories.UserRepository;
import br.com.uaifood.manager.exceptions.EmailAlreadyExistsException;
import br.com.uaifood.manager.exceptions.InvalidCredentialsException;
import br.com.uaifood.manager.exceptions.PasswordMismatchException;
import br.com.uaifood.manager.exceptions.WeakPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    private final UserRepository userRepository;
    private final PasswordPolicyService passwordPolicyService;
    private final PasswordEncoder passwordEncoder;

    public UserValidationService(
            UserRepository userRepository,
            PasswordPolicyService passwordPolicyService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordPolicyService = passwordPolicyService;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateRegistrationData(String email, String password, String confirmPassword) {
        ensureEmailNotExists(email);
        ensurePasswordStrength(password);
        ensurePasswordsMatch(password, confirmPassword);
    }

    public void ensureEmailNotExists(String email) {
        if (userRepository.existsByEmail(normalizeEmail(email))) {
            throw new EmailAlreadyExistsException();
        }
    }

    public void ensurePasswordStrength(String password) {
        if (!passwordPolicyService.isPasswordStrongEnough(password)) {
            throw new WeakPasswordException();
        }
    }

    public void ensurePasswordsMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException();
        }
    }

    public User validateLoginCredentials(String email, String password) {
        User user = findUserByEmailOrThrow(email, "E-mail ou senha incorretos");
        authenticatePassword(password, user.getPassword());

        return user;
    }

    public User findUserByEmailOrThrow(String email, String customMessage) {
        return userRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new InvalidCredentialsException(customMessage));
    }

    public void authenticatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidCredentialsException("E-mail ou senha incorretos");
        }
    }

    private String normalizeEmail(String email) {
        return email != null ? email.toLowerCase().trim() : null;
    }
}