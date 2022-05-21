package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormRepository;
import com.agh.emt.model.user.User;
import com.agh.emt.model.user.UserRepository;
import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.authentication.UserDetailsImpl;
import com.agh.emt.service.authentication.UserCredentialsService;
import com.agh.emt.service.one_drive.OneDriveService;
import com.agh.emt.service.student.StudentNotFoundException;
import com.agh.emt.utils.ranking.RankUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecruitmentFormService {
    private final RecruitmentFormRepository recruitmentFormRepository;
    private final UserRepository userRepository;
    private final OneDriveService oneDriveService;

    private static final int MAX_RECRUITMENT_FORMS_PER_STUDENT = 2;
    private static final String DEFAULT_RECRUITMENT_FORM_ONEDRIVE_LINK = "default-recruitment-form.pdf";

    public List<RecruitmentFormPreviewDTO> findAllPreviews() {
        List<RecruitmentFormPreview> recruitmentFormPreviews = recruitmentFormRepository.findAllProjectedBy();

        List<RecruitmentFormPreviewDTO> result = new LinkedList<>();

        byte[] pdf;
        double rank = 0;
        for (var recruitmentFormPreview: recruitmentFormPreviews) {
            try {
                pdf = oneDriveService.getRecruitmentFormPDF("somePath");
                rank = RankUtils.getRank(pdf);
            } finally {
                result.add(new RecruitmentFormPreviewDTO(recruitmentFormPreview, rank));
            }
        }
        return result;
    }

    public List<RecruitmentFormDTO> findForLoggedStudent() throws NoLoggedUserException, StudentNotFoundException {
        UserDetails loggedUser = UserCredentialsService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();

        return this.findForStudent(studentId);
    }

    public RecruitmentFormDTO addForLoggedStudent(RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException, RecruitmentFormNotFoundException, RecruitmentFormLimitExceededException {
        UserDetails loggedUser = UserCredentialsService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();

        return addForStudent(studentId, recruitmentFormDTO);
    }

    public RecruitmentFormDTO editForLoggedStudent(RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        UserDetails loggedUser = UserCredentialsService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();

        return editForStudent(studentId, recruitmentFormDTO);
    }

    public List<RecruitmentFormDTO> findForStudent(String studentId) throws StudentNotFoundException {
        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        List<RecruitmentForm> recruitmentForms = student.getRecruitmentForms();

        return recruitmentForms.stream()
                .map(form -> new RecruitmentFormDTO(form, oneDriveService.getRecruitmentFormPDF(form.getOneDriveLinkPdf())))
                .collect(Collectors.toList());
    }

    @Transactional
    public RecruitmentFormDTO addForStudent(String studentId, RecruitmentFormDTO recruitmentFormDTO) throws StudentNotFoundException, RecruitmentFormLimitExceededException, RecruitmentFormExistsException {
        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        if (student.getRecruitmentForms().size() == MAX_RECRUITMENT_FORMS_PER_STUDENT) {
            throw new RecruitmentFormLimitExceededException(
                    "Przekroczono limit zgłoszeń (" + MAX_RECRUITMENT_FORMS_PER_STUDENT + ") dla studenta: " + student.getEmail());
        }

        // check if already exists such priority
        Optional<RecruitmentForm> otherFormOpt = student.getRecruitmentForms()
                .stream()
                .filter(f -> f.getPriority().equals(recruitmentFormDTO.getPriority()))
                .findFirst();
        if (otherFormOpt.isPresent()) {
            throw new RecruitmentFormExistsException("Istnieje już formularz o priorytecie " + recruitmentFormDTO.getPriority() + " dla studenta: " + student.getEmail());
        }

        RecruitmentForm recruitmentForm = new RecruitmentForm();
        recruitmentForm = recruitmentFormRepository.save(recruitmentForm);
        String oneDriveLink = oneDriveService.postRecruitmentFormPDF(recruitmentForm.getId() + "/AR_" + new Timestamp(System.currentTimeMillis()) + ".pdf", recruitmentFormDTO.getPdf());

        recruitmentForm.setOneDriveLinkPdf(oneDriveLink);

        recruitmentForm.setPriority(recruitmentFormDTO.getPriority());
        recruitmentFormRepository.save(recruitmentForm);

        student.getRecruitmentForms().add(recruitmentForm);
        userRepository.save(student);

        return new RecruitmentFormDTO(recruitmentForm, oneDriveService.getRecruitmentFormPDF(recruitmentForm.getOneDriveLinkPdf()));
    }

    public RecruitmentFormDTO editForStudent(String studentId, RecruitmentFormDTO recruitmentFormDTO) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        RecruitmentForm recruitmentFormToEdit = recruitmentFormRepository.findById(recruitmentFormDTO.getId()).orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono formularza o id: " + recruitmentFormDTO.getId()));
        if (!student.getRecruitmentForms().contains(recruitmentFormToEdit)) {
            throw new RecruitmentFormNotFoundException("Nie znaleziono formularza o id: " + recruitmentFormDTO.getId() + " wśród formularzy studenta o id: " + studentId);
        }

        // to jednak nie potrzebne, bo kolejność na liście na froncie determinuje priorytet
//        // if different priority than before -> swap priorities with the already existent (if exists)
//        if (!recruitmentFormToEdit.getPriority().equals(recruitmentFormDTO.getPriority())) {
//            Optional<RecruitmentForm> otherFormOpt = student.getRecruitmentForms()
//                    .stream()
//                    .filter(f -> f.getPriority().equals(recruitmentFormDTO.getPriority()))
//                    .findFirst();
//
//            if (otherFormOpt.isPresent()) {
//                RecruitmentForm otherForm = otherFormOpt.get();
//                otherForm.setPriority(recruitmentFormToEdit.getPriority());
//                recruitmentFormRepository.save(otherForm);
//            }
//            recruitmentFormToEdit.setPriority(recruitmentFormDTO.getPriority());
//        }

        recruitmentFormToEdit.setTimeLastModified(LocalDateTime.now());
        recruitmentFormRepository.save(recruitmentFormToEdit);

        oneDriveService.putRecruitmentFormPDF(recruitmentFormToEdit.getOneDriveLinkPdf(), recruitmentFormDTO.getPdf());
        return new RecruitmentFormDTO(recruitmentFormToEdit, oneDriveService.getRecruitmentFormPDF(recruitmentFormToEdit.getOneDriveLinkPdf()));
    }

    public void deleteForStudent(String studentId, String formId) throws StudentNotFoundException {
        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        Optional<RecruitmentForm> recruitmentFormOpt = student.getRecruitmentForms()
                .stream()
                .filter(f -> f.getId().equals(formId))
                .findFirst();

        if (recruitmentFormOpt.isPresent()) {
            RecruitmentForm recruitmentForm = recruitmentFormOpt.get();
            recruitmentFormRepository.delete(recruitmentForm);

            student.getRecruitmentForms().remove(recruitmentForm);
            userRepository.save(student);
        }
    }

    public byte[] findDefaultRecruitmentForm() {
        return oneDriveService.getRecruitmentFormPDF(DEFAULT_RECRUITMENT_FORM_ONEDRIVE_LINK);
    }

    public byte[] addDefaultRecruitmentForm(byte[] pdf) {
        oneDriveService.postRecruitmentFormPDF(DEFAULT_RECRUITMENT_FORM_ONEDRIVE_LINK, pdf);
        return findDefaultRecruitmentForm();
    }

    public byte[] editDefaultRecruitmentForm(byte[] pdf) {
        return oneDriveService.putRecruitmentFormPDF(DEFAULT_RECRUITMENT_FORM_ONEDRIVE_LINK, pdf);
    }
}
