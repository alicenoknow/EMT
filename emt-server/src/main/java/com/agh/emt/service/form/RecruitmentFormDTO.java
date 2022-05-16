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

    byte[] pdf;

    public RecruitmentFormDTO(RecruitmentForm recruitmentForm, byte[] pdf) {
        this.id = recruitmentForm.getId();
        this.timeAdded = recruitmentForm.getTimeAdded();
        this.timeLastModified = recruitmentForm.getTimeLastModified();
        this.pdf = pdf;
    }
//    public RecruitmentFormDTO(byte[] pdf) {
//        this.id = "";
//        this.timeAdded = LocalDateTime.now();
//        this.timeLastModified = LocalDateTime.now();
//        this.pdf = pdf;
//    }
}
