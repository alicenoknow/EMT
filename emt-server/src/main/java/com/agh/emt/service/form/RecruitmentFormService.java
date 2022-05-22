package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormRepository;
import com.agh.emt.model.user.User;
import com.agh.emt.model.user.UserRepository;
import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.authentication.UserDetailsImpl;
import com.agh.emt.service.authentication.UserCredentialsService;
import com.agh.emt.service.one_drive.OneDriveConnectionException;
import com.agh.emt.service.one_drive.OneDriveService;
import com.agh.emt.service.one_drive.PostFileDTO;
import com.agh.emt.service.pdf_parser.PdfData;
import com.agh.emt.service.pdf_parser.PdfParserService;
import com.agh.emt.service.student.StudentNotFoundException;
import com.agh.emt.utils.form.Faculty;
import com.agh.emt.utils.ranking.RankUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    private final PdfParserService pdfParserService;

    private static final int MAX_RECUITMENT_FORMS_PER_STUDENT = 2;
    private static final String DEFAULT_RECRUITMENT_FORM_ONEDRIVE_LINK = "wzor/AnkietaRekrutacyjnaErasmus2022.pdf";

    public List<RecruitmentFormDoubleInfoDTO> findAllPreviews() {
        List<RecruitmentFormDoubleInfoDTO> recruitmentFormPreviews = recruitmentFormRepository.findAll().stream().map(RecruitmentFormDoubleInfoDTO::new).toList();

        List<RecruitmentFormDoubleInfoDTO> result = new LinkedList<>();

//        byte[] pdf;
//        double rank = 0;
        for (var recruitmentFormPreview: recruitmentFormPreviews) {
//            try {
//                pdf = oneDriveService.getRecruitmentDocumentFromPath("somePath");
//                rank = RankUtils.getRank(pdf);
//            } catch (RecruitmentFormNotFoundException e) {
//                e.printStackTrace();
//            } finally {
////                result.add(new RecruitmentFormPreviewDTO(recruitmentFormPreview, rank));
//
//            }
            result.add(recruitmentFormPreview);
        }
        return result;
    }

    public List<StudentFormsPreviewDTO> findAllStudentsWithPreviews() {
        List<User> users = userRepository.findAll();
        return users.stream().map(StudentFormsPreviewDTO::new).collect(Collectors.toList());
    }

    public RecruitmentFormDoubleInfoDTO findForLoggedStudent(Integer priority) throws NoLoggedUserException, StudentNotFoundException {
        UserDetails loggedUser = UserCredentialsService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();

        return this.findForStudent(studentId, priority);
    }

    public byte[] downloadScanForLoggedStudent(Integer priority) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        UserDetails loggedUser = UserCredentialsService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));;
        RecruitmentForm recruitmentForm = student.getRecruitmentForms().stream()
                .filter(recruitmentForm1 -> recruitmentForm1.getPriority().equals(priority))
                .findFirst().orElseThrow(() -> new StudentNotFoundException("Student " + studentId + "nie złożył ankiety z priorytetem: " + priority));
        return oneDriveService.getRecruitmentDocumentFromId(recruitmentForm.getOneDriveScanId());
    }

    public byte[] downloadFormForLoggedStudent(Integer priority) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        UserDetails loggedUser = UserCredentialsService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();
        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));;
        RecruitmentForm recruitmentForm = student.getRecruitmentForms().stream()
                .filter(recruitmentForm1 -> recruitmentForm1.getPriority().equals(priority))
                .findFirst().orElseThrow(() -> new StudentNotFoundException("Student " + studentId + "nie złożył ankiety z priorytetem: " + priority));
        return oneDriveService.getRecruitmentDocumentFromId(recruitmentForm.getOneDriveFormId());
    }

    public RecruitmentFormDTO addForLoggedStudent(RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException, RecruitmentFormNotFoundException, RecruitmentFormLimitExceededException {
        UserDetails loggedUser = UserCredentialsService.getLoggedUser();
        String studentId = ((UserDetailsImpl) loggedUser).getId();

        return addForStudent(studentId, recruitmentFormDTO);
    }
//    public RecruitmentFormDTO editForLoggedStudent(RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
//        UserDetails loggedUser = UserCredentialsService.getLoggedUser();
//        String studentId = ((UserDetailsImpl) loggedUser).getId();
//
//        return editForStudent(studentId, recruitmentFormDTO);
//    }

    public RecruitmentFormDoubleInfoDTO findForStudent(String studentId,Integer priority) throws StudentNotFoundException {
        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        List<RecruitmentForm> recruitmentForms = student.getRecruitmentForms();

        return recruitmentForms.stream()
                .map(RecruitmentFormDoubleInfoDTO::new)
                .filter(form -> form.getPriority().equals(priority))
                .collect(Collectors.toList()).get(0);
    }

    private boolean removeRecruitmentFormWithIdenticalPriority(User student, RecruitmentFormDTO recruitmentFormDTO){
        RecruitmentForm formId = student.getRecruitmentForms().stream()
                .filter(recruitmentForm -> recruitmentForm.getPriority().equals(recruitmentFormDTO.getPriority())).
                findFirst().orElse(null);
        System.out.println(formId);
        student.getRecruitmentForms().removeIf(recruitmentForm -> recruitmentForm.getPriority().equals(recruitmentFormDTO.getPriority()));
        student = userRepository.save(student);
        if(formId!=null)
            return recruitmentFormRepository.findAll()
                .removeIf(recruitmentForm1 -> recruitmentForm1.getId().equals(formId.getId()));
        return  false;
    }

    @Transactional
    public RecruitmentFormDTO addForStudent(String studentId, RecruitmentFormDTO recruitmentFormDTO) throws StudentNotFoundException, RecruitmentFormLimitExceededException {

        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        removeRecruitmentFormWithIdenticalPriority(student,recruitmentFormDTO);

        if ((student.getRecruitmentForms().size() == MAX_RECUITMENT_FORMS_PER_STUDENT ||
                recruitmentFormDTO.getPriority()>MAX_RECUITMENT_FORMS_PER_STUDENT ) && recruitmentFormDTO.getIsScan()) {
            throw new RecruitmentFormLimitExceededException("Przekroczono limit zgłoszeń dla studenta: " + student.getEmail());
        }

        RecruitmentForm recruitmentForm;
        String filename;
        if(recruitmentFormDTO.getIsScan()){
            recruitmentForm = recruitmentFormRepository.findById(recruitmentFormDTO.getId()).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono dokumentu o id: " + recruitmentFormDTO.getId()));
            filename =  student.getId() + "/" + recruitmentForm.getId() + "/AR_" + new Timestamp(System.currentTimeMillis()).toString()
                    .replace(":","-")
                    .replace(".","_") + "_scan.pdf";

        }else{
            recruitmentForm = new RecruitmentForm();
            recruitmentForm.setUser(student);
            recruitmentForm = recruitmentFormRepository.save(recruitmentForm);
            filename = student.getId() + "/" + recruitmentForm.getId() + "/AR_" + new Timestamp(System.currentTimeMillis()).toString()
                    .replace(":","-")
                    .replace(".","_") + ".pdf";
        }

        PostFileDTO oneDriveInfo = oneDriveService.postRecruitmentDocument(filename.replace(" ","_"), recruitmentFormDTO.getPdf());

        if(recruitmentFormDTO.getIsScan())
        {
            recruitmentForm.setOneDriveScanId(oneDriveInfo.getOneDriveId());
            recruitmentForm.setOneDriveScanPath(oneDriveInfo.getOneDrivePath());
            recruitmentForm.setOneDriveScanLink(oneDriveInfo.getOneDriveLink());
        }else{
            recruitmentForm.setOneDriveFormId(oneDriveInfo.getOneDriveId());
            recruitmentForm.setOneDriveFormPath(oneDriveInfo.getOneDrivePath());
            recruitmentForm.setOneDriveFormLink(oneDriveInfo.getOneDriveLink());

            student.getRecruitmentForms().add(recruitmentForm);

            //.......................................
            //TODO: attach parser service to get name, surname, department and main coordinator
            //.......................................

            try {
                PdfData pdfData = pdfParserService.getDataFromPdf(recruitmentFormDTO.getPdf());
                recruitmentForm.setSurname(pdfData.surname());
                recruitmentForm.setName(pdfData.name());
                recruitmentForm.setFaculty(pdfData.faculty());
                recruitmentForm.setCoordinator(pdfData.coordinator());

                student.setFaculty(Faculty.valueOf(pdfData.faculty()));
                student.setFirstName(pdfData.name());
                student.setLastName(pdfData.surname());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        recruitmentForm.setPriority(recruitmentFormDTO.getPriority());
        recruitmentFormRepository.save(recruitmentForm);



        userRepository.save(student);



        return new RecruitmentFormDTO(recruitmentForm,recruitmentFormDTO.getPdf(),recruitmentFormDTO.getIsScan());
    }
//    public RecruitmentFormDTO editForStudent(String studentId, RecruitmentFormDTO recruitmentFormDTO) throws RecruitmentFormNotFoundException, StudentNotFoundException {
//        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
//        RecruitmentForm recruitmentForm = recruitmentFormRepository.findById(recruitmentFormDTO.getId()).orElseThrow(() -> new RecruitmentFormNotFoundException("Nie znaleziono formularza o id: " + recruitmentFormDTO.getId()));
//        if (!student.getRecruitmentForms().contains(recruitmentForm)) {
//            throw new RecruitmentFormNotFoundException("Nie znaleziono formularza o id: " + recruitmentFormDTO.getId() + " wśród formularzy studenta o id: " + studentId);
//        }
//
//        oneDriveService.putRecruitmentFormPDF(recruitmentForm.getOneDriveLink(), recruitmentFormDTO.getPdf());
//        return new RecruitmentFormDTO(recruitmentForm, oneDriveService.getRecruitmentFormPdfFromPath(recruitmentForm.getOneDriveLink()));
//    }


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

    public byte[] findDefaultRecruitmentForm() throws RecruitmentFormNotFoundException {
        return oneDriveService.getRecruitmentDocumentFromPath(DEFAULT_RECRUITMENT_FORM_ONEDRIVE_LINK);
    }

    public byte[] addDefaultRecruitmentForm(byte[] pdf) throws RecruitmentFormNotFoundException {
        oneDriveService.postRecruitmentDocument(DEFAULT_RECRUITMENT_FORM_ONEDRIVE_LINK, pdf);
        return findDefaultRecruitmentForm();
    }

    public byte[] editDefaultRecruitmentForm(byte[] pdf) {
        return oneDriveService.putRecruitmentDocument(DEFAULT_RECRUITMENT_FORM_ONEDRIVE_LINK, pdf);
    }
}
