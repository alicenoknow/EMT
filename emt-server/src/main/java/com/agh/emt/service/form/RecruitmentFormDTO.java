package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class RecruitmentFormDTO {
    String id;
    LocalDateTime timeAdded;
    LocalDateTime timeLastModified;

    Integer priority;
    byte[] pdf;

    public RecruitmentFormDTO(RecruitmentForm recruitmentForm, byte[] pdf) {
        this.id = recruitmentForm.getId();
        this.timeAdded = recruitmentForm.getTimeAdded();
        this.timeLastModified = recruitmentForm.getTimeLastModified();
        this.priority = recruitmentForm.getPriority();
        this.pdf = pdf;
    }
}
