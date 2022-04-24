package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormPreview;
import com.agh.emt.model.form.RecruitmentFormRepository;
import com.agh.emt.model.student.Student;
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

    public RecruitmentForm findForLoggedStudent() throws NoLoggedUserException, RecruitmentFormNotFoundException, StudentNotFoundException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        return recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));
    }
    public RecruitmentForm addForLoggedStudent(RecruitmentForm recruitmentForm) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        if (recruitmentFormRepository.existsByStudent(student)) {
            throw new RecruitmentFormExistsException("Z tego konta złożono już formularz rekrutacyjny");
        }

        recruitmentForm.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono zalogowanego użytkownika w bazie")));
        return recruitmentFormRepository.save(recruitmentForm);
    }
    public RecruitmentForm editForLoggedStudent(RecruitmentForm newRecruitmentForm) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));

        recruitmentForm.updateFields(newRecruitmentForm);

        return recruitmentFormRepository.save(recruitmentForm);
    }

    public RecruitmentForm findForStudent(String studentId) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        return recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));
    }

    public RecruitmentForm addForStudent(String studentId, RecruitmentForm recruitmentForm) throws RecruitmentFormExistsException, StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        if (recruitmentFormRepository.existsByStudent(student)) {
            throw new RecruitmentFormExistsException("Z tego konta złożono już formularz rekrutacyjny");
        }

        recruitmentForm.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono użytkownika w bazie")));
        return recruitmentFormRepository.save(recruitmentForm);
    }
    public RecruitmentForm editForStudent(String studentId, RecruitmentForm newRecruitmentForm) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono formularza"));

        recruitmentForm.updateFields(newRecruitmentForm);

        return recruitmentFormRepository.save(recruitmentForm);
    }
}
