package com.agh.emt.model.form;

import com.agh.emt.model.student.Student;
import com.agh.emt.utils.form.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document("recruitment_form")
@Data
public class RecruitmentForm {
    @Id
    private Long id;
    private LocalDateTime timeAdded = LocalDateTime.now();
    private LocalDateTime timeLastModified;

    @DBRef
    private Student student; // imię, nazwisko, email

    private Faculty faculty; // can be determined if from different faculty
    private String homeFacultyCoordinator; // Enum?
    private String fieldOfStudy;
    private Integer yearOfStudy; // in [1, ..., 6]
    private Integer semestersFullyPassed; // in [1, ..., 10]
    private BigDecimal gpa;

    private String phoneNumber;
    private String contactEmail; // may be different from Student.email used for login
    private String homeAddress;

    private List<PlannedExchangeDetails> plannedExchangeDetails; // Current year: max. 2 exchanges

    private Boolean previousErasmus;
    private Integer previousErasmusMonths;
    private Integer previousErasmusDegree;

    private List<LanguageCertificate> certificates; // At least one?
    private MaintenanceGrantConfirmation maintenanceGrantConfirmation; // Optional
    private DisabilityCertificate disabilityCertificate; // Optional

    private Boolean isValid = false;
    private Boolean isNominated = false;

    @Transient
    public static final String SEQUENCE_NAME = "recruitment_form_sequence";
}
