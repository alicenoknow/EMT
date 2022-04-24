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

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecruitmentFormService {
    private final RecruitmentFormRepository recruitmentFormRepository;
    private final StudentRepository studentRepository;
    private final UserService userService;
    private final OneDriveService oneDriveService;

    public List<RecruitmentFormPreviewDTO> findAllPreviews() {
        List<RecruitmentFormPreview> recruitmentFormPreviews = recruitmentFormRepository.findAllProjectedBy();

        return recruitmentFormPreviews.stream().map(recruitmentFormPreview -> {
            //todo send request to one drive
            byte[] pdf = oneDriveService.getRecruitmentFormPDF("somePath");

            return new RecruitmentFormPreviewDTO(recruitmentFormPreview, RankUtils.getRank(pdf));
        }).collect(Collectors.toList());
    }

    public RecruitmentFormDTO findForLoggedStudent() throws NoLoggedUserException, RecruitmentFormNotFoundException, StudentNotFoundException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));

        //todo send GET request to one drive
        byte[] pdf = oneDriveService.getRecruitmentFormPDF("somePath");

        return new RecruitmentFormDTO(recruitmentForm, pdf);

    }
    public RecruitmentFormDTO addForLoggedStudent(RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException {
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

        //todo send POST request to one drive and get link
//        byte[] pdf,path = oneDriveService.postRecruitmentFormPDF("somePath");
        String oneDriveLink = "aaaaaaaa";

        recruitmentForm.setOneDriveLink(oneDriveLink);
        return recruitmentFormDTO;
    }
    public RecruitmentFormDTO editForLoggedStudent(RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        UserDetails loggedUser = UserService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));

        //todo send PUT request to one drive and get link
//        byte[] pdf,path = oneDriveService.putRecruitmentFormPDF("somePath");
        String oneDriveLink = "aaaaaaaa";
        recruitmentForm.setOneDriveLink(oneDriveLink);

        recruitmentFormRepository.save(recruitmentForm);

        return recruitmentFormDTO;
    }

    public RecruitmentFormDTO findForStudent(String studentId) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono Twojego formularza"));


        //todo send GET request to one drive
        byte[] pdf = oneDriveService.getRecruitmentFormPDF("somePath");

        return new RecruitmentFormDTO(recruitmentForm, pdf);

    }

    public RecruitmentFormDTO addForStudent(String studentId, RecruitmentFormDTO recruitmentFormDTO) throws RecruitmentFormExistsException, StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        if (recruitmentFormRepository.existsByStudent(student)) {
            throw new RecruitmentFormExistsException("Z tego konta złożono już formularz rekrutacyjny");
        }

        RecruitmentForm recruitmentForm = new RecruitmentForm();
        recruitmentForm.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono użytkownika w bazie")));

        //todo send POST request to one drive and get link
//        byte[] pdf,path = oneDriveService.postRecruitmentFormPDF("somePath");
        String oneDriveLink = "aaaaaaaa";

        recruitmentForm.setOneDriveLink(oneDriveLink);

        recruitmentFormRepository.save(recruitmentForm);
        return recruitmentFormDTO;
    }
    public RecruitmentFormDTO editForStudent(String studentId, RecruitmentFormDTO recruitmentFormDTO) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        RecruitmentForm recruitmentForm = recruitmentFormRepository.findByStudent(student)
                .orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono formularza"));


        //todo send PUT request to one drive and get link
//        byte[] pdf,path = oneDriveService.putRecruitmentFormPDF("somePath");
        String oneDriveLink = "aaaaaaaa";
        recruitmentForm.setOneDriveLink(oneDriveLink);

        recruitmentFormRepository.save(recruitmentForm);

        return recruitmentFormDTO;
    }
}
