package com.agh.emt.model.form;

import com.agh.emt.model.student.Student;
import com.agh.emt.service.form.RecruitmentFormPreview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitmentFormRepository extends MongoRepository<RecruitmentForm, String> {
    List<RecruitmentFormPreview> findAllProjectedBy();
}
