package com.agh.emt.model.form;

import com.agh.emt.model.student.Student;
import com.agh.emt.utils.form.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("recruitment_form")
@Data
public class RecruitmentForm {
    @Id
    private String id;
    private LocalDateTime timeAdded = LocalDateTime.now();
    private LocalDateTime timeLastModified = LocalDateTime.now();

    @DBRef
    private Student student; // imię, nazwisko, email

    private Faculty faculty; // can be determined if from different faculty
    private String homeFacultyCoordinator; // Enum?
    private String fieldOfStudy;
    private Integer yearOfStudy; // in [1, ..., 6]
    private Integer semestersFullyPassed; // in [1, ..., 10]
    private Double gpa;

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

    public void updateFields(RecruitmentForm other) {
        timeLastModified = LocalDateTime.now();
        faculty = other.getFaculty();
        homeFacultyCoordinator = other.getHomeFacultyCoordinator();
        fieldOfStudy = other.getFieldOfStudy();
        yearOfStudy = other.getYearOfStudy();
        semestersFullyPassed = other.getSemestersFullyPassed();
        gpa = other.getGpa();
        phoneNumber = other.getPhoneNumber();
        contactEmail = other.getContactEmail();
        homeAddress = other.getHomeAddress();
        plannedExchangeDetails = other.getPlannedExchangeDetails();
        previousErasmusMonths = other.getPreviousErasmusMonths();
        previousErasmusDegree = other.getPreviousErasmusDegree();
        certificates = other.getCertificates();
        maintenanceGrantConfirmation = other.getMaintenanceGrantConfirmation();
        disabilityCertificate = other.getDisabilityCertificate();
        isValid = other.getIsValid();
        isNominated = other.getIsNominated();
    }
}
