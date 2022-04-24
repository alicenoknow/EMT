package com.agh.emt.model.form;

import com.agh.emt.model.student.Student;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("recruitment_form")
@Data
public class RecruitmentForm {
    @Id
    private String id;
    private String oneDriveLink;
    private LocalDateTime timeAdded = LocalDateTime.now();
    private LocalDateTime timeLastModified = LocalDateTime.now();

    @DBRef
    private Student student; // imię, nazwisko, email
}
