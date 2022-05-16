package com.agh.emt.model.form;

import com.agh.emt.service.form.RecruitmentFormPreview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecruitmentFormRepository extends MongoRepository<RecruitmentForm, String> {
    List<RecruitmentFormPreview> findAllProjectedBy();
}
