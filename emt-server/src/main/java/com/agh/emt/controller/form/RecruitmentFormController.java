package com.agh.emt.controller.form;

import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.form.*;
import com.agh.emt.service.one_drive.OneDriveConnectionException;
import com.agh.emt.service.student.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment-form")
@AllArgsConstructor
public class RecruitmentFormController {
    private final RecruitmentFormService recruitmentFormService;

    private static final String DEFAULT_RECRUITMENT_FORM_FILENAME = "formularz-rekrutacyjny.pdf";

    @GetMapping("/form-list")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<List<RecruitmentFormPreviewDTO>> findAllPreviews() {
        return ResponseEntity.ok(recruitmentFormService.findAllPreviews());
    }

    @GetMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<List<RecruitmentFormDTO>> findForLoggedStudent() throws NoLoggedUserException, StudentNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.findForLoggedStudent());
    }

    @PostMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentFormDTO> addForLoggedStudent(@RequestBody RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException, RecruitmentFormNotFoundException, RecruitmentFormLimitExceededException, OneDriveConnectionException {
        return ResponseEntity.ok(recruitmentFormService.addForLoggedStudent(recruitmentFormDTO));
    }

    @PutMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentFormDTO> editForLoggedStudent(@RequestBody RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException, OneDriveConnectionException {
        return ResponseEntity.ok(recruitmentFormService.editForLoggedStudent(recruitmentFormDTO));
    }

    @GetMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<List<RecruitmentFormDTO>> findForStudent(@PathVariable String studentId) throws StudentNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.findForStudent(studentId));
    }

    @PostMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentFormDTO> addForStudent(@PathVariable String studentId, @RequestBody RecruitmentFormDTO recruitmentFormDTO) throws StudentNotFoundException, RecruitmentFormExistsException, RecruitmentFormNotFoundException, RecruitmentFormLimitExceededException, OneDriveConnectionException {
        return ResponseEntity.ok(recruitmentFormService.addForStudent(studentId, recruitmentFormDTO));
    }

    @PutMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentFormDTO> editForStudent(@PathVariable String studentId, @RequestBody RecruitmentFormDTO recruitmentFormDTO) throws RecruitmentFormNotFoundException, StudentNotFoundException, OneDriveConnectionException {
        return ResponseEntity.ok(recruitmentFormService.editForStudent(studentId, recruitmentFormDTO));
    }

    @DeleteMapping("/student-form/{studentId}/{formId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<?> deleteForStudent(@PathVariable String studentId, @PathVariable String formId) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        recruitmentFormService.deleteForStudent(studentId, formId);
        return ResponseEntity.ok().build();
    }


    private ResponseEntity<byte[]> getResponseForDefaultPdf(byte[] contents) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(DEFAULT_RECRUITMENT_FORM_FILENAME, DEFAULT_RECRUITMENT_FORM_FILENAME);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    @GetMapping("/default")
    ResponseEntity<byte[]> findDefaultRecruitmentForm() {
        byte[] contents = recruitmentFormService.findDefaultRecruitmentForm();
        return getResponseForDefaultPdf(contents);
    }

    @PostMapping("/default")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<byte[]> addDefaultRecruitmentForm(@RequestBody byte[] pdf) {
        byte[] contents = recruitmentFormService.addDefaultRecruitmentForm(pdf);
        return getResponseForDefaultPdf(contents);
    }

    @PutMapping("/default")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<byte[]> editDefaultRecruitmentForm(@RequestBody byte[] pdf) {
        byte[] contents = recruitmentFormService.editDefaultRecruitmentForm(pdf);
        return getResponseForDefaultPdf(contents);
    }
}
