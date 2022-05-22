package com.agh.emt.model.authentication;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {
    Optional<ConfirmationToken> findByConfirmationTokenString(String confirmationTokenString);
}
