package com.agh.emt.service.form;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class RecruitmentFormPreviewDTO {
    String id;
    String studentEmail;
    LocalDateTime timeAdded;
    LocalDateTime timeLastModified;
    Double rankingPoints;

    public RecruitmentFormPreviewDTO(RecruitmentFormPreview recruitmentFormPreview, Double rankingPoints) {
        this.id = recruitmentFormPreview.getId();
        this.studentEmail = recruitmentFormPreview.getUser().getEmail();
        this.timeAdded = recruitmentFormPreview.getTimeAdded();
        this.timeLastModified = recruitmentFormPreview.getTimeLastModified();
        this.rankingPoints = rankingPoints;
    }
}
