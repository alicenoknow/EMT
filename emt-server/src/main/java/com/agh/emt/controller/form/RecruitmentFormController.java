package com.agh.emt.controller.form;

import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.form.*;
import com.agh.emt.service.student.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

//    @GetMapping("/form-list")
//    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
//    ResponseEntity<List<RecruitmentFormPreviewDTO>> findAllPreviews() {
//        return ResponseEntity.ok(recruitmentFormService.findAllPreviews());
//    }

    @GetMapping("/student-list")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<List<StudentFormsPreviewDTO>> findAllStudentsWithPreviews() {
        return ResponseEntity.ok(recruitmentFormService.findAllStudentsWithPreviews());
    }
    @GetMapping
    ResponseEntity<List<RecruitmentFormPreviewDTO>> findAll() throws RecruitmentFormNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.findAllPreviews());
    }


//    @GetMapping
//    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
//    ResponseEntity<> findAllStudentsWithPreviews() {
        //todo zrobic jakies dto zeby byly dane podstawowe studenta i podstawowe jego formularzy i zwrocic liste wszystkich
//    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/my-form")
//    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<List<RecruitmentFormDTO>> findForLoggedStudent() throws NoLoggedUserException, StudentNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.findForLoggedStudent());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/my-form")
//    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentFormDTO> addForLoggedStudent(@RequestBody RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException, RecruitmentFormNotFoundException, RecruitmentFormLimitExceededException {
        return ResponseEntity.ok(recruitmentFormService.addForLoggedStudent(recruitmentFormDTO));
    }

    @PutMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentFormDTO> editForLoggedStudent(@RequestBody RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.editForLoggedStudent(recruitmentFormDTO));
    }

    @GetMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<List<RecruitmentFormDTO>> findForStudent(@PathVariable String studentId) throws StudentNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.findForStudent(studentId));
    }

    @PostMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentFormDTO> addForStudent(@PathVariable String studentId, @RequestBody RecruitmentFormDTO recruitmentFormDTO) throws StudentNotFoundException, RecruitmentFormExistsException, RecruitmentFormNotFoundException, RecruitmentFormLimitExceededException {
        return ResponseEntity.ok(recruitmentFormService.addForStudent(studentId, recruitmentFormDTO));
    }

    @PutMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentFormDTO> editForStudent(@PathVariable String studentId, @RequestBody RecruitmentFormDTO recruitmentFormDTO) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.editForStudent(studentId, recruitmentFormDTO));
    }

    @DeleteMapping("/student-form/{studentId}/{formId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<?> deleteForStudent(@PathVariable String studentId, @PathVariable String formId) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        recruitmentFormService.deleteForStudent(studentId, formId);
        return ResponseEntity.ok().build();
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/template-form")
    ResponseEntity<Resource> getTemplateForm() throws NoLoggedUserException, RecruitmentFormNotFoundException, StudentNotFoundException {
        HttpHeaders headers = new HttpHeaders();

        byte[] templateFormByte = recruitmentFormService.getTemplateForm();
        ByteArrayResource resource = new ByteArrayResource(templateFormByte);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(templateFormByte.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

//    @PostMapping("/template-form")
//    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
//    ResponseEntity<byte[]> addDefaultRecruitmentForm(@RequestBody byte[] pdf) {
//        byte[] contents = recruitmentFormService.addDefaultRecruitmentForm(pdf);
//        return getResponseForDefaultPdf(contents);
//    }
//
//    @PutMapping("/template-form")
//    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
//    ResponseEntity<byte[]> editDefaultRecruitmentForm(@RequestBody byte[] pdf) {
//        byte[] contents = recruitmentFormService.editDefaultRecruitmentForm(pdf);
//        return getResponseForDefaultPdf(contents);
//    }
}
