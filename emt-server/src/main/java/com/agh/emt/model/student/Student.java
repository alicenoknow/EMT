package com.agh.emt.model.student;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;


@Document("student")
@Data
public class Student {
    @Transient
    public static final String SEQUENCE_NAME = "student_sequence";

    @Id
    private BigInteger _id;
    private String email;
    private String password;
}
