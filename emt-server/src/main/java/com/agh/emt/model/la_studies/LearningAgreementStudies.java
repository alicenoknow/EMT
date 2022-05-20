package com.agh.emt.model.la_studies;


import com.agh.emt.model.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("la_studies")
@Data
public class LearningAgreementStudies {
    @Id
    private Long id;
    private LocalDateTime timeAdded = LocalDateTime.now();
    private LocalDateTime timeLastModified = LocalDateTime.now();

    @DBRef
    private User user; // imię, nazwisko, email

    // TODO: Fill fields as in LA document
}
