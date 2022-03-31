package com.agh.emt.model.student;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}
