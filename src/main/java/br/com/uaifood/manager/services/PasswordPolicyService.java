package br.com.uaifood.manager.services;

import org.springframework.stereotype.Service;

@Service
public class PasswordPolicyService {

    public boolean isPasswordStrongEnough(String password) {
        return password.length() >= 8 &&
                containsDigit(password) &&
                containsLetter(password) &&
                containsSpecialChar(password);
    }

    private boolean containsDigit(String password) {
        return password.matches(".*\\d.*");
    }

    private boolean containsLetter(String password) {
        return password.matches(".*[a-zA-Z].*");
    }

    private boolean containsSpecialChar(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
 }