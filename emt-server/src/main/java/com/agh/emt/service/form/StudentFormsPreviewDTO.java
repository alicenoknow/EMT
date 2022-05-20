package com.agh.emt.service.form;

import com.agh.emt.model.student.Student;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor
public class StudentFormsPreviewDTO {
    String studentId;
    List<RecruitmentFormLiteDTO> recruitmentFormLiteDTOList;

    public StudentFormsPreviewDTO(Student student) {
        this.studentId = student.getId();
        this.recruitmentFormLiteDTOList = student.getRecruitmentForms().stream().map(RecruitmentFormLiteDTO::new).collect(Collectors.toList());
    }
}
