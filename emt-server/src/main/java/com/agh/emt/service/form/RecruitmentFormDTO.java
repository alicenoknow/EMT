package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class RecruitmentFormDTO implements Serializable {
    String id;
    Integer priority;
    Boolean isScan;
    LocalDateTime timeAdded;
    LocalDateTime timeLastModified;

    byte[] pdf;

    public RecruitmentFormDTO(RecruitmentForm recruitmentForm, byte[] pdf, Boolean isScan) {
        this.id = recruitmentForm.getId();
        this.timeAdded = recruitmentForm.getTimeAdded();
        this.timeLastModified = recruitmentForm.getTimeLastModified();
        this.pdf = pdf;
        this.isScan = isScan;
        this.priority = recruitmentForm.getPriority();
    }
    public RecruitmentFormDTO(byte[] pdf, Integer priority ) {
        this.id = "";
        this.timeAdded = LocalDateTime.now();
        this.timeLastModified = LocalDateTime.now();
        this.pdf = pdf;
        this.priority = priority;
        this.isScan = false;
    }

    public RecruitmentFormDTO(String id, byte[] pdf, Integer priority, Boolean isScan ) {
        this.id = id;
        this.timeAdded = LocalDateTime.now();
        this.timeLastModified = LocalDateTime.now();
        this.pdf = pdf;
        this.priority = priority;
        this.isScan = isScan;
    }

}
