package com.agh.emt.service.pdf_parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.json.*;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PdfParserService {
    private final String pythonScriptsRoot = "parser_demo/scripts/";

    public PdfData getDataFromPdf(byte[] pdf) throws IOException, InterruptedException {
        // create temp dirs
        File tempPdf = new File(".getDataFromPdf_tempPdf");
        File tempJson = new File(".getDataFromPdf_tempJson");
        tempPdf.mkdir();
        tempJson.mkdir();

        // write pdf to temp file
        FileUtils.writeByteArrayToFile(new File(".getDataFromPdf_tempPdf/temp"), pdf);

        // convert pdf to json
        String[] command = {"python",
                            pythonScriptsRoot + "pdf_to_json.py",
                            ".getDataFromPdf_tempPdf/",
                            ".getDataFromPdf_tempJson/"};
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        System.out.println(new String(process.getErrorStream().readAllBytes()));
        System.out.println("Exit value: " + process.exitValue());
        System.out.println(new String(process.getInputStream().readAllBytes()));

        // read json
        String jsonString = new String(Files.readAllBytes(Paths.get(".getDataFromPdf_tempJson/temp_form.json")), StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(jsonString);
        PdfData pdfData = new PdfData(jsonObject.getString("Imie"),
                                      jsonObject.getString("Nazwisko"),
                                      jsonObject.getString("Wydzial"),
                                      jsonObject.getString("Koordynator Wydzia#C5#82owy"));

        // delete temp folder
        FileUtils.deleteDirectory(tempPdf);
        FileUtils.deleteDirectory(tempJson);
        (new File(".getDataFromPdf_tempJson/temp_form.json")).delete();
        (new File(".getDataFromPdf_tempPdf/temp")).delete();

        // return result
        return pdfData;
    }

    public byte[] parserPdfsToExcel(List<byte[]> pdfs) throws IOException, InterruptedException {
        // create temp dirs
        File tempPdf = new File(".parserPdfsToExcel_tempPdf");
        File tempJson = new File(".parserPdfsToExcel_tempJson");
        tempPdf.mkdir();
        tempJson.mkdir();

        // write pdfs to folder
        int i = 0;
        for (byte[] pdf : pdfs) {
            FileUtils.writeByteArrayToFile(new File(".parserPdfsToExcel_tempPdf/" + Integer.toString(i)), pdf);
            ++i;
        }



        // convert pdf to json
        String[] command = {"python",
                            pythonScriptsRoot + "pdf_form_extractor.py",
                            ".parserPdfsToExcel_temp.csv",
                            ".parserPdfsToExcel_tempPdf/",
                            ".parserPdfsToExcel_tempJson/"};
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        System.out.println(new String(process.getErrorStream().readAllBytes()));
        System.out.println("Exit value: " + process.exitValue());
        System.out.println(new String(process.getInputStream().readAllBytes()));

        // read csv
        byte[] csvData = Files.readAllBytes(Path.of(".parserPdfsToExcel_temp.csv"));

        // delete temp files
        FileUtils.deleteDirectory(tempPdf);
        FileUtils.deleteDirectory(tempJson);
        (new File(".parserPdfsToExcel_temp.csv")).delete();

        // return result
        return csvData;
    }
}
