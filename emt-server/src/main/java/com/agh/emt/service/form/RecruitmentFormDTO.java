package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentFormDTO {
    private String id;
    private LocalDateTime timeAdded;
    private LocalDateTime timeLastModified;

    private Integer priority;
    private byte[] pdf;

    public RecruitmentFormDTO(RecruitmentForm recruitmentForm, byte[] pdf) {
        this.id = recruitmentForm.getId();
        this.timeAdded = recruitmentForm.getTimeAdded();
        this.timeLastModified = recruitmentForm.getTimeLastModified();
        this.priority = recruitmentForm.getPriority();
        this.pdf = pdf;
    }
}
