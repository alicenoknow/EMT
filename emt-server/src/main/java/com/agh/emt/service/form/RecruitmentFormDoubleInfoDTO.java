package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class RecruitmentFormDoubleInfoDTO {
    String id;
    Integer priority;
    String oneDriveFormLink;
    String oneDriveFormPath;
    String oneDriveFormId;
    String oneDriveScanLink;
    String oneDriveScanPath;
    String oneDriveScanId;
    String surname;
    String name;
    String faculty;
    String coordinator;
    LocalDateTime timeAdded;
    LocalDateTime timeLastModified;

    public RecruitmentFormDoubleInfoDTO(RecruitmentForm recruitmentForm) {
        this.id = recruitmentForm.getId();
        this.priority = recruitmentForm.getPriority();
        oneDriveFormLink = recruitmentForm.getOneDriveFormLink();
        oneDriveFormPath = recruitmentForm.getOneDriveFormPath();
        oneDriveFormId = recruitmentForm.getOneDriveFormId();
        oneDriveScanLink = recruitmentForm.getOneDriveScanLink();
        oneDriveScanPath = recruitmentForm.getOneDriveScanPath();
        oneDriveScanId = recruitmentForm.getOneDriveScanId();
        timeAdded = recruitmentForm.getTimeAdded();
        timeLastModified = recruitmentForm.getTimeLastModified();
        surname = recruitmentForm.getSurname();
        name = recruitmentForm.getName();
        faculty = recruitmentForm.getFaculty();
        coordinator = recruitmentForm.getCoordinator();
    }
}
