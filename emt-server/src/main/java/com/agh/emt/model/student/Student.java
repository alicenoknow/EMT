package com.agh.emt.model.student;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document("student")
@Data
public class Student {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email; // only "*agh.edu.pl" emails accepted
    private String password;
    private LocalDateTime timeAdded = LocalDateTime.now();
}
