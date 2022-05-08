package com.agh.emt.service.one_drive;

import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class OneDriveService {
    public byte[] getRecruitmentFormPDF(String filePath) throws RecruitmentFormNotFoundException {
        //todo

        return getMock();
    }
    public byte[] postRecruitmentFormPDF(String filePath, byte[] pdf) throws RecruitmentFormNotFoundException {
        //todo

        return getMock();
    }
    public byte[] putRecruitmentFormPDF(String filePath, byte[] pdf) throws RecruitmentFormNotFoundException {
        //todo

        return getMock();
    }

    private byte[] getMock() throws RecruitmentFormNotFoundException {
        try {
            File file = ResourceUtils.getFile("classpath:default_document.pdf");
            InputStream in = new FileInputStream(file);
            return in.readAllBytes();
        } catch (IOException e) {
            throw new RecruitmentFormNotFoundException("nie znaleziono formularza...");
        }
    }
}
