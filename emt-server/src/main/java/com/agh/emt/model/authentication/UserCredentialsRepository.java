package com.agh.emt.model.authentication;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends MongoRepository<UserCredentials, String> {
    Optional<UserCredentials> findByEmail(String email);
}
