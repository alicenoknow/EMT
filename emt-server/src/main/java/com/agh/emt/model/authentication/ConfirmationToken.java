package com.agh.emt.model.authentication;

import com.agh.emt.model.user.User;
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
    private String confirmationTokenString = UUID.randomUUID().toString();
    private LocalDateTime timeCreated = LocalDateTime.now();

    @DBRef
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
    }
}
