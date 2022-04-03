package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormPreview;
import com.agh.emt.model.form.RecruitmentFormRepository;
import com.agh.emt.model.student.StudentRepository;
import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.authentication.UserDetailsImpl;
import com.agh.emt.service.authentication.UserService;
import com.agh.emt.service.student.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecruitmentFormService {
    private final RecruitmentFormRepository recruitmentFormRepository;
    private final StudentRepository studentRepository;
    private final UserService userService;

    public List<RecruitmentFormPreview> findAllPreviews() {
        return recruitmentFormRepository.findAllProjectedBy();
    }

    public RecruitmentForm findForLoggedStudent() throws NoLoggedUserException, RecruitmentFormNotFoundException {
        UserDetails loggedUser = UserService.getLoggedUser();

        return recruitmentFormRepository.findByStudent(((UserDetailsImpl) loggedUser).getId())
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));
    }
    public RecruitmentForm addForLoggedStudent(RecruitmentForm recruitmentForm) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();

        if (recruitmentFormRepository.existsByStudent(studentId)) {
            throw new RecruitmentFormExistsException("Z tego konta złożono już formularz rekrutacyjny");
        }

        recruitmentForm.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono zalogowanego użytkownika w bazie")));
        return recruitmentFormRepository.save(recruitmentForm);
    }
    public RecruitmentForm editForLoggedStudent(RecruitmentForm newRecruitmentForm) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();

        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(studentId)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));

        recruitmentForm.updateFields(newRecruitmentForm);

        return recruitmentFormRepository.save(recruitmentForm);
    }

    public RecruitmentForm findForStudent(String studentId) throws RecruitmentFormNotFoundException {
        return recruitmentFormRepository.findByStudent(studentId)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));
    }

    public RecruitmentForm addForStudent(String studentId, RecruitmentForm recruitmentForm) throws RecruitmentFormExistsException, StudentNotFoundException {
        if (recruitmentFormRepository.existsByStudent(studentId)) {
            throw new RecruitmentFormExistsException("Z tego konta złożono już formularz rekrutacyjny");
        }

        recruitmentForm.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono użytkownika w bazie")));
        return recruitmentFormRepository.save(recruitmentForm);
    }
    public RecruitmentForm editForStudent(String studentId, RecruitmentForm newRecruitmentForm) throws RecruitmentFormNotFoundException {
        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(studentId)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono formularza"));

        recruitmentForm.updateFields(newRecruitmentForm);

        return recruitmentFormRepository.save(recruitmentForm);
    }
}
