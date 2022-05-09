package com.agh.emt.service.authentication.email_sender;

import com.agh.emt.model.authentication.UserCredentials;
import com.agh.emt.model.confirmation_token.ConfirmationToken;
import com.agh.emt.model.confirmation_token.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailSenderService {
    private JavaMailSender javaMailSender;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Async
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        javaMailSender.send(simpleMailMessage);
    }

    public ConfirmationToken createConfirmationToken(UserCredentials userCredentials) {
        ConfirmationToken confirmationToken = new ConfirmationToken(userCredentials);
        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken;
    }

    public void sendConfirmationEmail(ConfirmationToken confirmationToken, UserCredentials userCredentials) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userCredentials.getEmail());
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