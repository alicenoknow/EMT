package com.agh.emt.service.one_drive;

import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class OneDriveService {
    private static GraphServiceClient<Request> graphClient;
    private static TokenCredentialAuthProvider authProvider;

    private final static String APPLICATION_ID = "f54c6665-00ac-4b6d-96c1-952c5781547c";
    private final static String ACCESS_TOKEN = "EwBwA8l6BAAUkj1NuJYtTVha+Mogk+HEiPbQo04AAbum9EJNI0+2xXVio0RH/O/M30XlsfuQMJmRBz3cdZe0nq/So7J/ldyfSXKIIf7TTp/RsPKy56XQfs5bAOoaMOQ35DLBn/y8Uc02bj9xLmdsGS0MSnNyZ9oPGdcp65+EICirxK0w/Aq5iz5HYdOzTOgbcowGCR5mI+JKX2b955YBUwMD1KQqTvL8MjnYFUl0kgkkmFLAflGQVuwKeQfppOXbqN/1Td35GkqeBR4muQM8FngtQbvqfQ7mzcnSv8QY2tldcFmNSiNT2Um+TqZoG/w9DpqiAkFJfvCS7CSYpsNucci/6MXxwbdJiSm2tVbSdE02E2Dt16KPbAWnAO8Wt2YDZgAACK7ZCaeR7BQqQAL9EiSUh+/8PJOTs7A7pSE12waxjEDqBcPzb+qXO7496k3pPetjVUgr1jos8+IhTgJ2ECOfbHfCz2wbxLWzyDjsVr1ta42mPdXJjXN9rTeXNHSbQ1DW+teVckY1d3B+WjT1ZgGlnzJ/nKxe35ZHsI4MYwZBzhcyrM+UtBbAHYTW6zEL8wKS5VbjAvO1BQNmtJxTmdAQhfqEr4DYRbEX/b7kja8xZVPVu9LHymKU3BK1N8eXXoGq9LlUejb2F6FEnWeMgBR686wRuJIsGNKPGvN2+xBiyxaIb0VfkKw0UtNmLM6T2fLqAagKZEP8DTFs/u2RdjXfuzx2EbRn0hDOV8GgIxZ2QPIaifl5P8X76pc4KQGyI920AOecmiRP0cS8VlAAXwP9InAA35KJ0mfteCG3kRfSBrwnDx10ByVH6s4eAv2+jiBwAOBpDLNw1waM33GX1OeuSLIoKS76h+OQnhomA+9Usop/UsZ1rnaedh9QTodMAFCNOaPxioURQz+cVrC4Z72d+bN5kRTD/UyF88MWUReXHsvxLeY5ggr1fkDBn/60S0WHgWPFSuegPSWepcXeQjwzRBvm6tZSEL0cdz6xal8DSQa+96rgyg4x7QHJjcPQbTyETZVz0vEYCPDP9uTcW96lZt11/3R+8X8amaMgBuDrSj07lH0CLoraC/xvO78hmCXfVrirc5CSqzaLjsmF3P4wnyA25yuRV+1yq3AuGUUJcDJnEurBB4i0eQmlpP8TjwiEKduBj7hRqJOO342KAg==";
    private final static String TENANT_ID = "f8cdef31-a31e-4b4a-93e4-5f571e91255a";
    private final static String SECRET_VALUE = "hmI8Q~lFOpUdrgm3Jvluum9Xg_ZJz7~ApdEG6aFj";
    private final static List<String> SCOPES = Arrays.asList("Files.Read", "Files.ReadWrite","Files.ReadWrite.AppFolder");

    private final RestTemplate restTemplate;


    @Autowired
    public OneDriveService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public byte[] getRecruitmentFormPDF(String fileId) throws RecruitmentFormNotFoundException {
        String url = "https://graph.microsoft.com/v1.0/me/drive/items/" + fileId + "/content" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ACCESS_TOKEN);
        HttpEntity request = new HttpEntity(headers);

        var response  = restTemplate.exchange(url, HttpMethod.GET, request, byte[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(response.getBody());
        } else {
            return null;
        }
    }
    public String postRecruitmentFormPDF(String filePath, byte[] pdf) throws RecruitmentFormNotFoundException {
        String url = "https://graph.microsoft.com/v1.0/me/drive/root:/" + filePath + ":/content" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ACCESS_TOKEN);
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdf.length);

        LinkedMultiValueMap<String, byte[]> map = new LinkedMultiValueMap<>();
        map.add("file", pdf);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(pdf, headers);
        RestTemplate restTemplate = new RestTemplate();
        var response  = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println(response.getBody());
            return (String) new JSONObject(response.getBody()).get("id");
        } else {
            return null;
        }
    }
    public byte[] putRecruitmentFormPDF(String fileId, byte[] pdf) throws RecruitmentFormNotFoundException {
        String url = "https://graph.microsoft.com/v1.0/me/drive/items/" + fileId + "/content" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ACCESS_TOKEN);
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdf.length);

        LinkedMultiValueMap<String, byte[]> map = new LinkedMultiValueMap<>();
        map.add("file", pdf);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(pdf, headers);
        RestTemplate restTemplate = new RestTemplate();
        var response  = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println(response.getBody());
            return Objects.requireNonNull(response.getBody()).getBytes(StandardCharsets.UTF_8);
        } else {
            return null;
        }
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
