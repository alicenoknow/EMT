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
    private String oneDriveLinkPdf;
    private String oneDriveLinkScan;
    private LocalDateTime timeAdded = LocalDateTime.now();
    private LocalDateTime timeLastModified = LocalDateTime.now();
    private Integer priority;

    @DBRef(lazy = true)
    private User user;
}
