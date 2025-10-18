package br.com.uaifood.manager.services;

import br.com.uaifood.manager.domain.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class PasswordResetService {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private final JavaMailSender mailSender;

    public PasswordResetService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return calendar.getTime();
    }

    public void sendPasswordResetEmail(User user, String token) {
        String resetUrl = frontendUrl + "/reset-password?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Redefinição de senha - UAIFood");
        email.setText(constructEmailText(user.getName(), resetUrl));

        mailSender.send(email);
    }

    private String constructEmailText(String userName, String resetUrl) {
        return String.format(
                "Olá %s,\n\nRecebemos uma solicitação para redefinir sua senha.\n\n" +
                        "Para continuar, clique no link abaixo:\n%s\n\n" +
                        "Se você não solicitou esta alteração, por favor ignore este email.\n\n" +
                        "Atenciosamente,\nEquipe UAIFood",
                userName, resetUrl
        );
    }
}