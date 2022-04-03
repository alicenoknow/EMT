package com.agh.emt.model.form;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitmentFormRepository extends MongoRepository<RecruitmentForm, String> {
    List<RecruitmentFormPreview> findAllProjectedBy();

    Optional<RecruitmentForm> findByStudent(String id);
    boolean existsByStudent(String id);
}
