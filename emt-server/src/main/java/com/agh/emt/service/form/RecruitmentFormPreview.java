package com.agh.emt.service.form;

import com.agh.emt.model.student.Student;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class RecruitmentFormPreview {
    String id;
    LocalDateTime timeAdded;
    LocalDateTime timeLastModified;
    Student student;
}
