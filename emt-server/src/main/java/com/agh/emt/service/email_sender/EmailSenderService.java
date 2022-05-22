package com.agh.emt.service.email_sender;

import com.agh.emt.model.authentication.ConfirmationToken;
import com.agh.emt.model.authentication.ConfirmationTokenRepository;
import com.agh.emt.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Async
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        javaMailSender.send(simpleMailMessage);
    }

    public ConfirmationToken createConfirmationToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken;
    }

    public void sendConfirmationEmail(ConfirmationToken confirmationToken, User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Potwierdź swoją rejestrację w EMT");
        mailMessage.setFrom("no-reply@emt.agh.edu.pl"); // w skrzynce pokazuje się emt.io.agh@gmail.com jako nadawca :(
        mailMessage.setText("Aby potwierdzić swoje konto, kliknij w poniższy link: "
                + "http://localhost:8080" + "/api/auth/confirm-account?token=" + confirmationToken.getConfirmationTokenString());
        sendEmail(mailMessage);
    }

    public ConfirmationToken getConfirmationTokenByTokenString(String confirmationTokenString)
            throws NoSuchConfirmationTokenException {

        Optional<ConfirmationToken> confirmationToken =
                confirmationTokenRepository.findByConfirmationTokenString(confirmationTokenString);
        if(confirmationToken.isPresent()) {
            return confirmationToken.get();
        }
        else {
            throw new NoSuchConfirmationTokenException("Podanego tokenu nie ma w bazie danych");
        }
    }
}
