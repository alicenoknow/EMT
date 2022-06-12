package com.agh.emt.service.excel_lists;

import com.agh.emt.model.form.RecruitmentFormRepository;
import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.one_drive.OneDriveService;
import com.agh.emt.service.one_drive.PostFileDTO;
import com.agh.emt.service.parameters.ParameterNotFoundException;
import com.agh.emt.service.parameters.ParameterService;
import com.agh.emt.service.pdf_parser.PdfParserService;
import com.agh.emt.utils.parameters.ParameterNames;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelListsService {
    OneDriveService oneDriveService;
    RecruitmentFormRepository recruitmentFormRepository;
    PdfParserService pdfParserService;
    ParameterService parameterService;

    final static String RESULT_EXCEL_PATH = "/results/ErasmusRecruitmentResults2022.csv";
    final static String RESULT_DWZ_EXCEL_PATH = "/results/ErasmusRecruitmentResults2022.csv";


    public PostFileDTO generateRecruitmentResults() throws ParameterNotFoundException {
        String erasmusEdition = parameterService.findParameter(ParameterNames.ERASMUS_EDITION).getValue();
        List<byte[]> recruitmentFormsPdfs =  recruitmentFormRepository.findAll().stream()
                .filter(recruitmentForm -> recruitmentForm!=null &&
                        recruitmentForm.getOneDriveFormPath()!=null &&
                        recruitmentForm.getOneDriveFormPath().contains(erasmusEdition))
                .map(recruitmentForm -> {
                    try {
                        return oneDriveService.getRecruitmentDocumentFromId(recruitmentForm.getOneDriveFormId());
                    } catch (RecruitmentFormNotFoundException e) {
                        e.printStackTrace();
                    }
                    return "".getBytes(StandardCharsets.UTF_8);
            }).toList();

        //.......................................
        //TODO: attach parser service to generate
        //.......................................

        byte[] excelRecruitmentResultList  = "".getBytes(StandardCharsets.UTF_8);
        try {
            excelRecruitmentResultList = pdfParserService.parserPdfsToExcel(recruitmentFormsPdfs);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

//        byte[] excelRecruitmentResultList = "".getBytes(StandardCharsets.UTF_8); //mock

        return oneDriveService.postRecruitmentDocument(RESULT_EXCEL_PATH,excelRecruitmentResultList);

    }

    public byte[] downloadRecruitmentResults() throws RecruitmentFormNotFoundException {
        return oneDriveService.getRecruitmentDocumentFromPath(RESULT_EXCEL_PATH);
    }

    public PostFileDTO modifyRecruitmentResults(byte[] excel){
        return oneDriveService.postRecruitmentDocument(RESULT_EXCEL_PATH, excel);
    }

    public PostFileDTO generateDWZRecruitmentResults() throws RecruitmentFormNotFoundException {
        byte[] recruitmentResults = oneDriveService.getRecruitmentDocumentFromPath(RESULT_EXCEL_PATH);

        byte[] excelRecruitmentResultDWZ  = "".getBytes(StandardCharsets.UTF_8);
        try {
            excelRecruitmentResultDWZ = pdfParserService.excelToDWZExcel(recruitmentResults);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return oneDriveService.postRecruitmentDocument(RESULT_DWZ_EXCEL_PATH, excelRecruitmentResultDWZ);
    }

    public byte[] downloadRecruitmentResultsDWZ() throws RecruitmentFormNotFoundException {
        return oneDriveService.getRecruitmentDocumentFromPath(RESULT_DWZ_EXCEL_PATH);
    }
}
