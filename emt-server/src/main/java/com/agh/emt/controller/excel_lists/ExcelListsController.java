package com.agh.emt.controller.excel_lists;

import com.agh.emt.service.excel_lists.ExcelListsService;
import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.form.RecruitmentFormService;
import com.agh.emt.service.one_drive.PostFileDTO;
import com.agh.emt.service.parameters.ParameterNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/excel-lists")
@CrossOrigin("http://localhost:3000/")
@AllArgsConstructor
public class ExcelListsController {
    ExcelListsService excelListsService;

    @GetMapping("/results")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<PostFileDTO> getRecruitmentResults() throws RecruitmentFormNotFoundException, ParameterNotFoundException {
        return ResponseEntity.ok(excelListsService.generateRecruitmentResults());
    }

    @GetMapping("/results/download")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<byte[]> downloadRecruitmentResults() throws RecruitmentFormNotFoundException {
        byte[] contents = excelListsService.downloadRecruitmentResults();
        return getResponseForDefaultPdf(contents);
    }

    @RequestMapping(value = "/results" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<PostFileDTO> uploadModifiedResults(@RequestParam("excel") MultipartFile excel) throws RecruitmentFormNotFoundException, IOException {
        return ResponseEntity.ok(excelListsService.modifyRecruitmentResults(excel.getBytes()));
    }

    @GetMapping("/results-DWZ")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<PostFileDTO> getRecruitmentResultsDWZ() throws RecruitmentFormNotFoundException {
        try{
            return ResponseEntity.ok(excelListsService.generateDWZRecruitmentResults());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(excelListsService.generateDWZRecruitmentResults());
        }

    }

    @GetMapping("/results-DWZ/download")
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<byte[]> downloadRecruitmentResultsDWZ() throws RecruitmentFormNotFoundException {
        byte[] contents = excelListsService.downloadRecruitmentResultsDWZ();
        return getResponseForDefaultPdf(contents);
    }

    private ResponseEntity<byte[]> getResponseForDefaultPdf(byte[] contents) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("formularz-rekrutacyjny.pdf", "formularz-rekrutacyjny.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

}
