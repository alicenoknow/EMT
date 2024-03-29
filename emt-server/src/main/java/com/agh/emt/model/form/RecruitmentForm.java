package com.agh.emt.model.form;

import com.agh.emt.model.user.User;
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
    private String oneDriveFormLink;
    private String oneDriveFormPath;
    private String oneDriveFormId;
    private String oneDriveScanLink;
    private String oneDriveScanPath;
    private String oneDriveScanId;
    private Integer priority;
    private String surname;
    private String name;
    private String faculty;
    private String coordinator;
    private LocalDateTime timeAdded = LocalDateTime.now();
    private LocalDateTime timeLastModified = LocalDateTime.now();

    @DBRef(lazy = true)
    private User user;
}
