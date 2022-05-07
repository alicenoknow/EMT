package com.agh.emt;

import com.agh.emt.service.authentication.email_sender.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;

@SpringBootApplication
public class EmtApplication {
	@Autowired
	private EmailSenderService emailSenderService;

	public static void main(String[] args) {
		SpringApplication.run(EmtApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sendTestMail() {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("byczekassassin@gmail.com");
		mailMessage.setSubject("[TEST] LMAO ESSA AMOGUS [TEST]");
		mailMessage.setFrom("no-reply@emt.agh.edu.pl"); // w skrzynce pokazuje się emt.io.agh@gmail.com jako adresat :(
		mailMessage.setText("Aby potwierdzić swoje konto, wklej ponizszy adres do wyszukiwarki: "
				+ "http://localhost:3000" + "/confirm-account?token=" + "lmao");
		emailSenderService.sendEmail(mailMessage);
	}
}
