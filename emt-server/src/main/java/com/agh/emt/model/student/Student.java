package com.agh.emt.model.student;

import com.agh.emt.model.authentication.UserCredentials;
import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.utils.form.Faculty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("student")
@Data
public class Student {
    @Id
    private String id;
//    private String firstName;
//    private String lastName;
    private Faculty faculty;
    private LocalDateTime timeAdded = LocalDateTime.now();

    @DBRef
    private UserCredentials userCredentials;

    @DBRef
    private List<RecruitmentForm> recruitmentForms;
}
