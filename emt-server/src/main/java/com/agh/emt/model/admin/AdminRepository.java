package com.agh.emt.model.admin;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, BigInteger> {
    Optional<Admin> findByEmail(String email);
}
