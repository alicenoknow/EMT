package com.agh.emt.model.form;

import com.agh.emt.model.student.Student;
import com.agh.emt.utils.form.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
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
    private LocalDateTime timeAdded;

    @DBRef
    private Student student; // imię, nazwisko, wydział, stopień, rok, kierunek studiów itp...
    // czy miejsce z innego wydziału niż macierzysty? (tak/nie) -> można stwierdzić na podstawie studenta

    private Faculty faculty; // can be determined if from different faculty
    private String homeFacultyCoordinator; // Enum?
    private String fieldOfStudy;
    private Integer yearOfStudy; // in [1, ..., 6]
    private Integer semestersFullyPassed; // in [1, ..., 10]
    private BigDecimal gpa;

    private String phoneNumber;
    private String contactEmail; // may be different from Student.email used for login
    private String homeAddress;

    private ErasmusDestination erasmusDestination;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Boolean longTerm;

    private Boolean previousErasmus;
    private Integer previousErasmusMonths;
    private Integer previousErasmusDegree;

    private List<LanguageCertificate> certificates; // At least one?
    private MaintenanceGrantConfirmation maintenanceGrantConfirmation; // Optional
    private DisabilityCertificate disabilityCertificate; // Optional

    private Boolean accepted = false;
}
