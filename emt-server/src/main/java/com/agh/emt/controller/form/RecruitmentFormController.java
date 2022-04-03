package com.agh.emt.controller.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormPreview;
import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.form.RecruitmentFormExistsException;
import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.form.RecruitmentFormService;
import com.agh.emt.service.student.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment-form")
@AllArgsConstructor
public class RecruitmentFormController {
    private final RecruitmentFormService recruitmentFormService;

    @GetMapping
    ResponseEntity<List<RecruitmentFormPreview>> findAll() {
        return ResponseEntity.ok(recruitmentFormService.findAllPreviews());
    }

    @GetMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentForm> findForLoggedStudent() throws NoLoggedUserException, RecruitmentFormNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.findForLoggedStudent());
    }

    @PostMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentForm> addForLoggedStudent(@RequestBody RecruitmentForm recruitmentForm) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException {
        return ResponseEntity.ok(recruitmentFormService.addForLoggedStudent(recruitmentForm));
    }

    @PutMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentForm> editForLoggedStudent(@RequestBody RecruitmentForm recruitmentForm) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.editForLoggedStudent(recruitmentForm));
    }


    @GetMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentForm> findForUser(@PathVariable String studentId) throws RecruitmentFormNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.findForStudent(studentId));
    }

    @PostMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentForm> addForUser(@PathVariable String studentId, @RequestBody RecruitmentForm recruitmentForm) throws StudentNotFoundException, RecruitmentFormExistsException {
        return ResponseEntity.ok(recruitmentFormService.addForStudent(studentId, recruitmentForm));
    }

    @PutMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentForm> editForUser(@PathVariable String studentId, @RequestBody RecruitmentForm recruitmentForm) throws RecruitmentFormNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.editForStudent(studentId, recruitmentForm));
    }
}
