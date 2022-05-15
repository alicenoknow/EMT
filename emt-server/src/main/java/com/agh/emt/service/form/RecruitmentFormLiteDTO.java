package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class RecruitmentFormLiteDTO {
    String id;
    String oneDriveLink;
    LocalDateTime timeAdded;
    LocalDateTime timeLastModified;

    public RecruitmentFormLiteDTO(RecruitmentForm recruitmentForm) {
        this.id = recruitmentForm.getId();
        this.timeAdded = recruitmentForm.getTimeAdded();
        this.timeLastModified = recruitmentForm.getTimeLastModified();
        this.oneDriveLink = recruitmentForm.getOneDriveLink();
    }
}
