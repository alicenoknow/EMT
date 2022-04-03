package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormPreview;
import com.agh.emt.model.form.RecruitmentFormRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@AllArgsConstructor
public class RecruitmentFormService {
    private final RecruitmentFormRepository recruitmentFormRepository;

    public List<RecruitmentFormPreview> findAllPreviews() {
        return recruitmentFormRepository.findAllProjectedBy();
    }

    public RecruitmentForm findForLoggedUser() {
        return null;
    }
    public RecruitmentForm addForLoggedUser(RecruitmentForm recruitmentForm) {
        return null;
    }
    public RecruitmentForm editForLoggedUser(RecruitmentForm recruitmentForm) {
        return null;
    }
    public RecruitmentForm findForUser(BigInteger userId) {
        return null;
    }
    public RecruitmentForm addForUser(BigInteger userId, RecruitmentForm recruitmentForm) {
        return null;
    }
    public RecruitmentForm editForUser(BigInteger userId, RecruitmentForm recruitmentForm) {
        return null;
    }
}
