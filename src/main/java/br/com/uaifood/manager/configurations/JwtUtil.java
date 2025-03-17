package br.com.uaifood.manager.configurations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") private String secret;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    private final Algorithm algorithm = Algorithm.HMAC256(secret);
    private final JWTVerifier verifier = JWT.require(algorithm).build();

    public String generateToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String extractUserId(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public boolean isTokenValid(String token, String userId) {
        String extractedId = extractUserId(token);
        return extractedId != null && extractedId.equals(userId);
    }
}
