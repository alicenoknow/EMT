package com.agh.emt.model.form;

import com.agh.emt.model.student.Student;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class RecruitmentFormPreview {
    Long id;
    LocalDateTime timeAdded;
    LocalDateTime timeLastModified;
    Student student;
}
