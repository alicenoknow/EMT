package com.agh.emt.model.admin;

import com.agh.emt.model.student.Student;
import com.agh.emt.utils.admin.AdminRole;
import com.agh.emt.utils.form.Faculty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document("admin")
@Data
public class Admin {
    @Id
    private Integer id;
    private AdminRole adminRole;
    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email; // only "*agh.edu.pl" emails accepted

    private String passwordHash;
    private Faculty faculty; // Optional

    @DBRef
    private List<Student> students;

    @Transient
    public static final String SEQUENCE_NAME = "admin_sequence";
}
