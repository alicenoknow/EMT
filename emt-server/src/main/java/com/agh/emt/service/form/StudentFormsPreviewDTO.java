package com.agh.emt.service.form;

import com.agh.emt.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor
public class StudentFormsPreviewDTO {
    String studentId;
    List<RecruitmentFormLiteDTO> recruitmentFormLiteDTOList;

    public StudentFormsPreviewDTO(User user) {
        this.studentId = user.getId();
        this.recruitmentFormLiteDTOList = user.getRecruitmentForms().stream().map(RecruitmentFormLiteDTO::new).collect(Collectors.toList());
    }
}
