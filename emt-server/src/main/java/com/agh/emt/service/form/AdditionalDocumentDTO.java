package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class AdditionalDocumentDTO implements Serializable {
    String name;
    byte[] doc;

    public AdditionalDocumentDTO(byte[] doc, String name) {
        this.doc = doc;
        this.name = name;
    }

}
