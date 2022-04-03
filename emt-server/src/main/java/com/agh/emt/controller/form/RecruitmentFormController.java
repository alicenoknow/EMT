package com.agh.emt.controller.form;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormPreview;
import com.agh.emt.service.form.RecruitmentFormService;
import com.agh.emt.utils.form.FormType;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
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
    ResponseEntity<RecruitmentForm> findForLoggedUser() {
        return ResponseEntity.ok(recruitmentFormService.findForLoggedUser());
    }

    @PostMapping("/my-form")
    ResponseEntity<RecruitmentForm> addForLoggedUser(@RequestBody RecruitmentForm recruitmentForm) {
        return ResponseEntity.ok(recruitmentFormService.addForLoggedUser(recruitmentForm));
    }

    @PutMapping("/my-form")
    ResponseEntity<RecruitmentForm> editForLoggedUser(@RequestBody RecruitmentForm recruitmentForm) {
        return ResponseEntity.ok(recruitmentFormService.editForLoggedUser(recruitmentForm));
    }


    @GetMapping("/user-form/{userId}")
    ResponseEntity<RecruitmentForm> findForUser(@PathVariable BigInteger userId) {
        return ResponseEntity.ok(recruitmentFormService.findForUser(userId));
    }

    @PostMapping("/user-form/{userId}")
    ResponseEntity<RecruitmentForm> addForUser(@PathVariable BigInteger userId, @RequestBody RecruitmentForm recruitmentForm) {
        return ResponseEntity.ok(recruitmentFormService.addForUser(userId, recruitmentForm));
    }

    @PutMapping("/user-form/{userId}")
    ResponseEntity<RecruitmentForm> editForUser(@PathVariable BigInteger userId, @RequestBody RecruitmentForm recruitmentForm) {
        return ResponseEntity.ok(recruitmentFormService.editForUser(userId, recruitmentForm));
    }
}
