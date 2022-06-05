package com.agh.emt;

import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.one_drive.OneDriveService;
import com.agh.emt.service.pdf_parser.PdfData;
import com.agh.emt.service.pdf_parser.PdfParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class SomeTest {
    @Test
    void testTrueIsTrue() {
        Assertions.assertTrue(true);
    }

    @Test
    void postOneDriveTest() throws RecruitmentFormNotFoundException, IOException {
        OneDriveService oneDriveService = new OneDriveService(new RestTemplateBuilder());
//        Path pdfPath = Paths.get("C:\\Users\\Fisiek\\Downloads\\sth.pdf");
//        byte[] pdf = Files.readAllBytes(pdfPath);
//        oneDriveService.postRecruitmentFormPDF("FolderA/sth.pdf",pdf);
//        oneDriveService.getRecruitmentFormPDF("FolderA/FileD.pdf");
    }

    @Test
    void pdfParserGetDataFromPdf() throws IOException, InterruptedException {
        PdfParserService pdfParserService = new PdfParserService();
        byte[] pdf = Files.readAllBytes(Path.of("parser_demo/parsers_example/pdfs/JanKowalski.pdf"));
        PdfData pdfData = pdfParserService.getDataFromPdf(pdf);
        System.out.println(pdfData.name());
        System.out.println(pdfData.surname());
        System.out.println(pdfData.faculty());
        System.out.println(pdfData.coordinator());
    }

    @Test
    void pdfParserParserPdfsToExcel() throws IOException, InterruptedException {
        PdfParserService pdfParserService = new PdfParserService();
        byte[] pdf0 = Files.readAllBytes(Path.of("parser_demo/parsers_example/pdfs/JanKowalski.pdf"));
        byte[] pdf1 = Files.readAllBytes(Path.of("parser_demo/parsers_example/pdfs/JanNowak.pdf"));
        byte[] pdf2 = Files.readAllBytes(Path.of("parser_demo/parsers_example/pdfs/MarcinDomański.pdf"));
        List<byte[]> pdfs = new LinkedList<byte[]>();
        pdfs.add(pdf0);
        pdfs.add(pdf1);
        pdfs.add(pdf2);
        byte[] excel = pdfParserService.parserPdfsToExcel(pdfs);
        System.out.println(excel.length);
    }

    @Test
    void pdfParserExcelToDWZExcel() throws IOException, InterruptedException {
        PdfParserService pdfParserService = new PdfParserService();
        byte[] csv = Files.readAllBytes(Path.of("parser_demo/csv_results/result.csv"));
        byte[] dwz = pdfParserService.excelToDWZExcel(csv);
        System.out.println(dwz.length);
    }
}
