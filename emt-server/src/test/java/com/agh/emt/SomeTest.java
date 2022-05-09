package com.agh.emt;

import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.one_drive.OneDriveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
