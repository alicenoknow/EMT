package com.agh.emt.controller.excel_lists;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/excel-lists")
@AllArgsConstructor
public class ExcelListsController {
    @GetMapping("/results")
    ResponseEntity<byte[]> getRecrutmentResults() {
        byte[] contents = recruitmentFormService.findDefaultRecruitmentForm();
        return getResponseForDefaultPdf(contents);
    }

    @PutMapping("/results")
    ResponseEntity<byte[]> chan() {
        byte[] contents = recruitmentFormService.findDefaultRecruitmentForm();
        return getResponseForDefaultPdf(contents);
    }

}
