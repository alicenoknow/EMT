package com.agh.emt.model.confirmation_token;

import com.agh.emt.model.authentication.UserCredentials;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document("confirmation_token")
@Data
public class ConfirmationToken {
    @Id
    private String id;
    private String confirmationTokenString;
    private LocalDateTime timeCreated;
    @DBRef
    private UserCredentials userCredentials;

    public ConfirmationToken(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
        timeCreated = LocalDateTime.now();
        confirmationTokenString = UUID.randomUUID().toString();
    }
}
