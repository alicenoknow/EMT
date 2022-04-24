package com.agh.emt;

import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.one_drive.OneDriveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
public class SomeTest {
    @Test
    void testTrueIsTrue() {
        Assertions.assertTrue(true);
    }

    @Test
    void postOneDriveTest() throws RecruitmentFormNotFoundException {
        OneDriveService oneDriveService = new OneDriveService();

        oneDriveService.postRecruitmentFormPDF("test","".getBytes(StandardCharsets.UTF_8));
    }
}
