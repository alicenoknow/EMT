package com.agh.emt.model.admin;

import com.agh.emt.model.authentication.UserCredentials;
import com.agh.emt.utils.form.Faculty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("admin")
@Data
public class Admin {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Faculty faculty;
    @DBRef
    private UserCredentials userCredentials;
}
