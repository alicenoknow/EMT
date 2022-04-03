package com.agh.emt.model.student;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, BigInteger> {
    Optional<Student> findByEmail(String email);
}
