package com.agh.emt.service.form;

import com.agh.emt.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class RecruitmentFormPreview {
    String id;
    LocalDateTime timeAdded;
    LocalDateTime timeLastModified;
    User user;
}
