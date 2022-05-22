package com.agh.emt.service.form;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class RecruitmentFormPreviewDTO {
    String id;
    LocalDateTime timeAdded;
    LocalDateTime timeLastModified;
    Double rankingPoints;
    String firstName;
    String lastName;
    String major;
    String faculty;
    String contractCoordinator;
    Integer priority;
    String oneDriveLinkPdf;
    String oneDriveLinkScan;

    public RecruitmentFormPreviewDTO(RecruitmentFormPreview recruitmentFormPreview, Double rankingPoints) {
        this.id = recruitmentFormPreview.getId();
        this.timeAdded = recruitmentFormPreview.getTimeAdded();
        this.timeLastModified = recruitmentFormPreview.getTimeLastModified();
        this.rankingPoints = rankingPoints;
        this.priority = recruitmentFormPreview.getPriority();
        this.firstName = "Jan";  // TODO: To trzeba wyciągnąć ze sparsowanego PDFa wg Alicji
                                 // TODO: może jednak po prostu email? xd
        this.lastName = "Paweł";
        this.major = "Informatyka";
        this.faculty = "WiET";
        this.contractCoordinator = "Anna Kowalska";
        this.oneDriveLinkPdf = recruitmentFormPreview.getOneDriveLinkPdf();
        this.oneDriveLinkScan = recruitmentFormPreview.getOneDriveLinkScan();
    }
}
