package com.agh.emt.model.user;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.utils.authentication.Role;
import com.agh.emt.utils.form.Faculty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("user")
@Data
public class User {
    @Id
    private String id;
    private String email;
    @JsonIgnore
    private String password;
    private Role role;
    private boolean isEnabled = false;

    private String firstName;
    private String lastName;
    private Faculty faculty;

    private LocalDateTime timeAdded = LocalDateTime.now();

    @DBRef
    private List<RecruitmentForm> recruitmentForms = List.of();
}
