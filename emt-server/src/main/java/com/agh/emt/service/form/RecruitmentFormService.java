package com.agh.emt.service.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormRepository;
import com.agh.emt.model.parameters.Parameter;
import com.agh.emt.model.parameters.ParameterRepository;
import com.agh.emt.model.user.User;
import com.agh.emt.model.user.UserRepository;
import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.authentication.UserCredentialsService;
import com.agh.emt.service.authentication.UserDetailsImpl;
import com.agh.emt.service.one_drive.OneDriveService;
import com.agh.emt.service.one_drive.PostFileDTO;
import com.agh.emt.service.parameters.ParameterFormatException;
import com.agh.emt.service.parameters.ParameterNotFoundException;
import com.agh.emt.service.parameters.ParameterService;
import com.agh.emt.service.pdf_parser.PdfData;
import com.agh.emt.service.pdf_parser.PdfParserService;
import com.agh.emt.service.student.StudentNotFoundException;
import com.agh.emt.utils.date.DateUtils;
import com.agh.emt.utils.form.Faculty;
import com.agh.emt.utils.parameters.ParameterNames;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecruitmentFormService {
    private final RecruitmentFormRepository recruitmentFormRepository;
    private final ParameterRepository parameterRepository;
    private final ParameterService parameterService;
    private final UserRepository userRepository;
    private final OneDriveService oneDriveService;
    private final PdfParserService pdfParserService;

    private static final String DEFAULT_RECRUITMENT_FORM_ONEDRIVE_LINK = "wzor/AnkietaRekrutacyjnaErasmus2022.pdf";

    public List<RecruitmentFormDoubleInfoDTO> findAllPreviews() throws ParameterNotFoundException {
        String erasmusEdition = parameterService.findParameter(ParameterNames.ERASMUS_EDITION).getValue();
        List<RecruitmentFormDoubleInfoDTO> recruitmentFormPreviews = recruitmentFormRepository.findAll().stream()
                .filter(recruitmentForm -> recruitmentForm!=null &&
                        recruitmentForm.getOneDriveFormPath()!=null &&
                        recruitmentForm.getOneDriveFormPath().contains(erasmusEdition))
                .map(RecruitmentFormDoubleInfoDTO::new).toList();

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

    private void validateDate() throws DateValidationException, ParameterFormatException {
        LocalDateTime now = LocalDateTime.now();
        Optional<Parameter> startDateOpt = parameterRepository.findById(ParameterNames.RECRUITMENT_START_DATE);
        Optional<Parameter> endDateOpt = parameterRepository.findById(ParameterNames.RECRUITMENT_END_DATE);

//        if (startDateOpt.isEmpty() || endDateOpt.isEmpty())
//            throw new DateValidationException("Brak skonfigurowanych dat rozpoczecia i / lub końca rekrutacji. Skontaktuj się z administratorem");
//
//        LocalDateTime startDate, endDate;
//        try {
//            startDate = DateUtils.parseDate(startDateOpt.get().getValue());
//            endDate = DateUtils.parseDate(endDateOpt.get().getValue());
//        } catch (DateTimeParseException e) {
//            throw new ParameterFormatException("Błędna wartość parametru rozpoczecia i / lub końca rekrutacji. Skontaktuj się z administratorem");
//        }
//
//        if (now.isBefore(startDate) || now.isAfter(endDate)) {
//            throw new DateValidationException("Błąd: przesłano dokument przed / po okresie rekrutacyjnym");
//        }
    }

    public List<StudentFormsPreviewDTO> findAllStudentsWithPreviews() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(StudentFormsPreviewDTO::new)
                .collect(Collectors.toList());
    }

    public RecruitmentFormDoubleInfoDTO findForLoggedStudent(Integer priority) throws NoLoggedUserException, StudentNotFoundException, ParameterNotFoundException {
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

    public RecruitmentFormDTO addForLoggedStudent(RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException, RecruitmentFormNotFoundException, RecruitmentFormLimitExceededException, DateValidationException, ParameterFormatException, ParameterNotFoundException {
        validateDate();

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

    public RecruitmentFormDoubleInfoDTO findForStudent(String studentId,Integer priority) throws StudentNotFoundException, ParameterNotFoundException {
        String erasmusEdition = parameterService.findParameter(ParameterNames.ERASMUS_EDITION).getValue();
        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        List<RecruitmentForm> recruitmentForms = student.getRecruitmentForms();

        return recruitmentForms.stream()
                .map(RecruitmentFormDoubleInfoDTO::new)
                .filter(form -> form.getPriority().equals(priority))
                .filter(recruitmentForm -> recruitmentForm.getOneDriveFormPath() != null &&
                        recruitmentForm.getOneDriveFormPath().contains(erasmusEdition))
                .collect(Collectors.toList()).get(0);
    }

    private boolean removeRecruitmentFormWithIdenticalPriority(User student, RecruitmentFormDTO recruitmentFormDTO){
        if(recruitmentFormDTO.getIsScan())
            return false;
        RecruitmentForm formId = student.getRecruitmentForms().stream()
                .filter(recruitmentForm -> {
                    return recruitmentFormDTO.getPriority().equals(recruitmentForm.getPriority());
                }).
                findFirst().orElse(null);
        try {
            oneDriveService.deleteRecruitmentDocumentFromId(formId.getOneDriveFormId());
            oneDriveService.deleteRecruitmentDocumentFromId(formId.getOneDriveScanId());
        } catch (Exception ignored) {}

        student.getRecruitmentForms().removeIf(recruitmentForm -> (recruitmentForm!=null && recruitmentFormDTO.getPriority().equals(recruitmentForm.getPriority())));
        student = userRepository.save(student);
        if(formId!=null){
            recruitmentFormRepository.deleteById(formId.getId());
            return true;
        }

        return  false;
    }

    @Transactional
    public RecruitmentFormDTO addForStudent(String studentId, RecruitmentFormDTO recruitmentFormDTO) throws StudentNotFoundException, RecruitmentFormLimitExceededException, DateValidationException, ParameterNotFoundException, ParameterFormatException {
        validateDate();

        User student = userRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));

        try{
            removeRecruitmentFormWithIdenticalPriority(student,recruitmentFormDTO);
        }catch (Exception e){
            e.printStackTrace();
        }

        String maxRecruitmentFormsPerStudentStr = parameterRepository.findById(ParameterNames.MAX_RECUITMENT_FORMS_PER_STUDENT).orElseThrow(() -> new ParameterNotFoundException("Błąd: nie skonfigurowano limitu formularzy dla studenta. Skontaktuj się z administratorem")).getValue();
        int maxRecruitmentFormsPerStudent;
        try {
            maxRecruitmentFormsPerStudent = Integer.parseInt(maxRecruitmentFormsPerStudentStr);
        } catch (NumberFormatException e) {
            throw new ParameterFormatException("Zły format parametru: " + ParameterNames.MAX_RECUITMENT_FORMS_PER_STUDENT);
        }

        if ((student.getRecruitmentForms().size() == maxRecruitmentFormsPerStudent ||
                recruitmentFormDTO.getPriority()>maxRecruitmentFormsPerStudent ) && !recruitmentFormDTO.getIsScan()) {
            throw new RecruitmentFormLimitExceededException("Przekroczono limit zgłoszeń dla studenta: " + student.getEmail());
        }

        RecruitmentForm recruitmentForm;
        String filename;
        String erasmusEdition = parameterService.findParameter(ParameterNames.ERASMUS_EDITION).getValue();
        if(recruitmentFormDTO.getIsScan()){
            recruitmentForm = recruitmentFormRepository.findById(recruitmentFormDTO.getId()).orElseThrow(() -> new StudentNotFoundException("Nie znaleziono dokumentu o id: " + recruitmentFormDTO.getId()));
            filename =  ParameterNames.ERASMUS_EDITION + "/" + erasmusEdition + "/" + student.getEmail() + "/" + recruitmentFormDTO.getPriority() + "/AR_" + new Timestamp(System.currentTimeMillis()).toString()
                    .replace(":","-")
                    .replace(".","_") + "_scan.pdf";

        }else{
            recruitmentForm = new RecruitmentForm();
            recruitmentForm.setUser(student);
            recruitmentForm = recruitmentFormRepository.save(recruitmentForm);
            filename = ParameterNames.ERASMUS_EDITION + "/" + erasmusEdition + "/" + student.getEmail() + "/" + recruitmentFormDTO.getPriority() + "/AR_" + new Timestamp(System.currentTimeMillis()).toString()
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
