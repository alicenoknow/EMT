package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormRepository;
import com.agh.emt.model.student.Student;
import com.agh.emt.model.student.StudentRepository;
import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.authentication.UserDetailsImpl;
import com.agh.emt.service.authentication.UserService;
import com.agh.emt.service.one_drive.OneDriveService;
import com.agh.emt.service.student.StudentNotFoundException;
import com.agh.emt.utils.ranking.RankUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecruitmentFormService {
    private final RecruitmentFormRepository recruitmentFormRepository=null;
    private final StudentRepository studentRepository=null;
    private final UserService userService=null;
    private final OneDriveService oneDriveService=null;

    public List<RecruitmentFormPreviewDTO> findAllPreviews() {
        List<RecruitmentFormPreview> recruitmentFormPreviews = recruitmentFormRepository.findAllProjectedBy();

        List<RecruitmentFormPreviewDTO> result = new LinkedList<>();

        byte[] pdf;
        double rank = 0;
        for (var recruitmentFormPreview: recruitmentFormPreviews) {
            try {
                pdf = oneDriveService.getRecruitmentFormPDF("somePath");
                rank = RankUtils.getRank(pdf);
            } catch (RecruitmentFormNotFoundException e) {
                rank = 0;
            } finally {
                result.add(new RecruitmentFormPreviewDTO(recruitmentFormPreview, rank));
            }
        }
        return result;
    }

    public RecruitmentFormDTO findForLoggedStudent() throws NoLoggedUserException, RecruitmentFormNotFoundException, StudentNotFoundException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));

        byte[] pdf = oneDriveService.getRecruitmentFormPDF("somePath");

        return new RecruitmentFormDTO(recruitmentForm, pdf);

    }
    public RecruitmentFormDTO addForLoggedStudent(RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException, RecruitmentFormNotFoundException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        if (recruitmentFormRepository.existsByStudent(student)) {
            throw new RecruitmentFormExistsException("Z tego konta złożono już formularz rekrutacyjny");
        }

        RecruitmentForm recruitmentForm = new RecruitmentForm();
        recruitmentForm.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono zalogowanego użytkownika w bazie")));
        recruitmentFormRepository.save(recruitmentForm);

        String oneDriveLink= oneDriveService.postRecruitmentFormPDF(studentId + "/AR_" + new Timestamp(System.currentTimeMillis())+ ".pdf", recruitmentFormDTO.getPdf());

        recruitmentForm.setOneDriveLink(oneDriveLink);
        return recruitmentFormDTO;
    }
    public RecruitmentFormDTO editForLoggedStudent(RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));


        oneDriveService.putRecruitmentFormPDF(recruitmentForm.getOneDriveLink(), recruitmentFormDTO.getPdf());

        recruitmentFormRepository.save(recruitmentForm);

        return recruitmentFormDTO;
    }

    public RecruitmentFormDTO findForStudent(String studentId) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));


        byte[] pdf = oneDriveService.getRecruitmentFormPDF(recruitmentForm.getOneDriveLink());

        return new RecruitmentFormDTO(recruitmentForm, pdf);

    }

    public RecruitmentFormDTO addForStudent(String studentId, RecruitmentFormDTO recruitmentFormDTO) throws RecruitmentFormExistsException, StudentNotFoundException, RecruitmentFormNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        if (recruitmentFormRepository.existsByStudent(student)) {
            throw new RecruitmentFormExistsException("Z tego konta złożono już formularz rekrutacyjny");
        }

        RecruitmentForm recruitmentForm = new RecruitmentForm();
        recruitmentForm.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono użytkownika w bazie")));

        String oneDriveLink = oneDriveService.postRecruitmentFormPDF(studentId+studentId + "/AR_" + new Timestamp(System.currentTimeMillis())+ ".pdf", recruitmentFormDTO.getPdf());

        recruitmentForm.setOneDriveLink(oneDriveLink);

        recruitmentFormRepository.save(recruitmentForm);
        return recruitmentFormDTO;
    }
    public RecruitmentFormDTO editForStudent(String studentId, RecruitmentFormDTO recruitmentFormDTO) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono formularza"));


        oneDriveService.putRecruitmentFormPDF(recruitmentForm.getOneDriveLink(), recruitmentFormDTO.getPdf());
//        todo
        String oneDriveLink = "aaaaaaaa";
        recruitmentForm.setOneDriveLink(oneDriveLink);

        recruitmentFormRepository.save(recruitmentForm);

        return recruitmentFormDTO;
    }
}
