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
        File tempPdf = new File(".tempPdf");
        File tempJson = new File(".tempJson");
        tempPdf.mkdir();
        tempJson.mkdir();

        // write pdf to temp file
        FileUtils.writeByteArrayToFile(new File(".tempPdf/temp"), pdf);

        // convert pdf to json
        String[] command = {"python",
                pythonScriptsRoot + "pdf_to_json.py",
                ".tempPdf/",
                ".tempJson/"};
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        System.out.println(new String(process.getErrorStream().readAllBytes()));
        System.out.println("Exit value: " + process.exitValue());
        System.out.println(new String(process.getInputStream().readAllBytes()));

        // read json
        String jsonString = new String(Files.readAllBytes(Paths.get(".tempJson/temp_form.json")), StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(jsonString);
        PdfData pdfData = new PdfData(jsonObject.getString("Imie"),
                jsonObject.getString("Nazwisko"),
                jsonObject.getString("Wydzial"),
                jsonObject.getString("Koordynator Wydzia#C5#82owy"));

        // delete temp folder
        FileUtils.deleteDirectory(tempPdf);
        FileUtils.deleteDirectory(tempJson);
        
        // return result
        return pdfData;
    }

    public byte[] parserPdfsToExcel(List<byte[]> pdfs) throws IOException, InterruptedException {
        // create temp dirs
        File tempPdf = new File(".tempPdf");
        File tempJson = new File(".tempJson");
        tempPdf.mkdir();
        tempJson.mkdir();

        // write pdfs to folder
        int i = 0;
        for (byte[] pdf : pdfs) {
            FileUtils.writeByteArrayToFile(new File(".tempPdf/temp" + Integer.toString(i)), pdf);
            ++i;
        }

        // convert pdf to json
        String[] commandPdfToJson = {"python3",
                pythonScriptsRoot + "pdf_to_json.py",
                ".tempPdf/",
                ".tempJson/"};
        Process process = Runtime.getRuntime().exec(commandPdfToJson);
        process.waitFor();
        
        // convert json to csv
        String[] commandJsonToCsv = {"python3",
                pythonScriptsRoot + "json_to_csv.py",
                ".temp.csv",
                pythonScriptsRoot + "json_fields_specs.json",
                ".tempJson/",
                "get_rank"};
        process = Runtime.getRuntime().exec(commandJsonToCsv);
        process.waitFor();
        
        System.out.println(new String(process.getErrorStream().readAllBytes()));
        System.out.println("Exit value: " + process.exitValue());
        System.out.println(new String(process.getInputStream().readAllBytes()));

        // read csv
        byte[] csvData = Files.readAllBytes(Path.of(".temp.csv"));

        // delete temp files
        FileUtils.deleteDirectory(tempPdf);
        FileUtils.deleteDirectory(tempJson);
        (new File(".temp.csv")).delete();

        // return result
        return csvData;
    }
    
    public byte[] excelToDWZExcel(byte[] excel) throws IOException, InterruptedException {
        // write excel to file
        FileUtils.writeByteArrayToFile(new File(".temp_excel.csv"), excel);
        
        // convert excel to DWZ excel
        String[] command = {"python3",
                pythonScriptsRoot + "dwz_export.py",
                ".temp_excel.csv",
                pythonScriptsRoot + "../parsers_example/scripts/form.xlsx",
                ".temp_dwz_excel.xlsx"};
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        
        System.out.println(new String(process.getErrorStream().readAllBytes()));
        System.out.println("Exit value: " + process.exitValue());
        System.out.println(new String(process.getInputStream().readAllBytes()));

        // read dwz excel
        byte[] dwzData = Files.readAllBytes(Path.of(".temp_dwz_excel.xlsx"));

        // delete temp files
        (new File(".temp_excel.csv")).delete();
        (new File(".temp_dwz_excel.xlsx")).delete();

        // return result
        return dwzData;
    }
}
