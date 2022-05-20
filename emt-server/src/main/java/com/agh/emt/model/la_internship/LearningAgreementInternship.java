package com.agh.emt.model.la_internship;


import com.agh.emt.model.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("la_internship")
@Data
public class LearningAgreementInternship {
    @Id
    private Long id;
    private LocalDateTime timeAdded = LocalDateTime.now();
    private LocalDateTime timeLastModified = LocalDateTime.now();

    @DBRef
    private User user; // imię, nazwisko, email

    // TODO: Fill fields as in LA document
}
