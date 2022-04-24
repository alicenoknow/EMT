package com.agh.emt.controller.form;

import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.form.*;
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
    ResponseEntity<List<RecruitmentFormPreviewDTO>> findAll() {
        return ResponseEntity.ok(recruitmentFormService.findAllPreviews());
    }

    @GetMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentFormDTO> findForLoggedStudent() throws NoLoggedUserException, RecruitmentFormNotFoundException, StudentNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.findForLoggedStudent());
    }

    @PostMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentFormDTO> addForLoggedStudent(@RequestBody RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormExistsException {
        return ResponseEntity.ok(recruitmentFormService.addForLoggedStudent(recruitmentFormDTO));
    }

    @PutMapping("/my-form")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<RecruitmentFormDTO> editForLoggedStudent(@RequestBody RecruitmentFormDTO recruitmentFormDTO) throws NoLoggedUserException, StudentNotFoundException, RecruitmentFormNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.editForLoggedStudent(recruitmentFormDTO));
    }


    @GetMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentFormDTO> findForUser(@PathVariable String studentId) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.findForStudent(studentId));
    }

    @PostMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentFormDTO> addForUser(@PathVariable String studentId, @RequestBody RecruitmentFormDTO recruitmentFormDTO) throws StudentNotFoundException, RecruitmentFormExistsException {
        return ResponseEntity.ok(recruitmentFormService.addForStudent(studentId, recruitmentFormDTO));
    }

    @PutMapping("/student-form/{studentId}")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<RecruitmentFormDTO> editForUser(@PathVariable String studentId, @RequestBody RecruitmentFormDTO recruitmentFormDTO) throws RecruitmentFormNotFoundException, StudentNotFoundException {
        return ResponseEntity.ok(recruitmentFormService.editForStudent(studentId, recruitmentFormDTO));
    }
}
