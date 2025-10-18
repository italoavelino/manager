package br.com.uaifood.manager.configurations;

import br.com.uaifood.manager.domain.model.User;
import br.com.uaifood.manager.exceptions.InvalidTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expirationMs}")
    private Long expirationMs;

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole())
                .withClaim("name", user.getName())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationMs))
                .sign(getAlgorithm());
    }

    private DecodedJWT validateAndDecode(String token) {
        try {
            return JWT.require(getAlgorithm()).build().verify(token);
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException();
        }
    }

    public boolean isTokenValid(String token) {
        try {
            validateAndDecode(token);
            return true;
        } catch (InvalidTokenException e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        return validateAndDecode(token).getSubject();
    }

    public String extractEmail(String token) {
        return validateAndDecode(token).getClaim("email").asString();
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}
