package com.agh.emt.model.authentication;

import com.agh.emt.utils.authentication.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user_credentials")
@Data
public class UserCredentials {
    @Id
    private String id;
    private String email;
    @JsonIgnore
    private String password;
    private Role role;
    private boolean isEnabled = false;
}
