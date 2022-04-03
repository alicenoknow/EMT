package com.agh.emt.model.form;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface RecruitmentFormRepository extends MongoRepository<RecruitmentForm, Long> {
    List<RecruitmentFormPreview> findAllProjectedBy();

    @Query(value = "{ 'student._id' : ?0 }")
    Optional<RecruitmentForm> findByStudentId(BigInteger id);
}
