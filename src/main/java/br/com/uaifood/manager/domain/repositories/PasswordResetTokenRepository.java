package br.com.uaifood.manager.domain.repositories;

import br.com.uaifood.manager.domain.model.PasswordResetToken;
import br.com.uaifood.manager.domain.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    PasswordResetToken findByToken(String token);
    void deleteByUserId(String userId);
}

