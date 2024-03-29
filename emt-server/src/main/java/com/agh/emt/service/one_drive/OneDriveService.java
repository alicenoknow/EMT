package com.agh.emt.service.one_drive;

import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import lombok.AllArgsConstructor;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@AllArgsConstructor
public class OneDriveService {
    private static GraphServiceClient<Request> graphClient;
    private static TokenCredentialAuthProvider authProvider;

    private final static String APPLICATION_ID = "f54c6665-00ac-4b6d-96c1-952c5781547c";
//    private final static String ACCESS_TOKEN = "EwBwA8l6BAAUkj1NuJYtTVha+Mogk+HEiPbQo04AAbum9EJNI0+2xXVio0RH/O/M30XlsfuQMJmRBz3cdZe0nq/So7J/ldyfSXKIIf7TTp/RsPKy56XQfs5bAOoaMOQ35DLBn/y8Uc02bj9xLmdsGS0MSnNyZ9oPGdcp65+EICirxK0w/Aq5iz5HYdOzTOgbcowGCR5mI+JKX2b955YBUwMD1KQqTvL8MjnYFUl0kgkkmFLAflGQVuwKeQfppOXbqN/1Td35GkqeBR4muQM8FngtQbvqfQ7mzcnSv8QY2tldcFmNSiNT2Um+TqZoG/w9DpqiAkFJfvCS7CSYpsNucci/6MXxwbdJiSm2tVbSdE02E2Dt16KPbAWnAO8Wt2YDZgAACK7ZCaeR7BQqQAL9EiSUh+/8PJOTs7A7pSE12waxjEDqBcPzb+qXO7496k3pPetjVUgr1jos8+IhTgJ2ECOfbHfCz2wbxLWzyDjsVr1ta42mPdXJjXN9rTeXNHSbQ1DW+teVckY1d3B+WjT1ZgGlnzJ/nKxe35ZHsI4MYwZBzhcyrM+UtBbAHYTW6zEL8wKS5VbjAvO1BQNmtJxTmdAQhfqEr4DYRbEX/b7kja8xZVPVu9LHymKU3BK1N8eXXoGq9LlUejb2F6FEnWeMgBR686wRuJIsGNKPGvN2+xBiyxaIb0VfkKw0UtNmLM6T2fLqAagKZEP8DTFs/u2RdjXfuzx2EbRn0hDOV8GgIxZ2QPIaifl5P8X76pc4KQGyI920AOecmiRP0cS8VlAAXwP9InAA35KJ0mfteCG3kRfSBrwnDx10ByVH6s4eAv2+jiBwAOBpDLNw1waM33GX1OeuSLIoKS76h+OQnhomA+9Usop/UsZ1rnaedh9QTodMAFCNOaPxioURQz+cVrC4Z72d+bN5kRTD/UyF88MWUReXHsvxLeY5ggr1fkDBn/60S0WHgWPFSuegPSWepcXeQjwzRBvm6tZSEL0cdz6xal8DSQa+96rgyg4x7QHJjcPQbTyETZVz0vEYCPDP9uTcW96lZt11/3R+8X8amaMgBuDrSj07lH0CLoraC/xvO78hmCXfVrirc5CSqzaLjsmF3P4wnyA25yuRV+1yq3AuGUUJcDJnEurBB4i0eQmlpP8TjwiEKduBj7hRqJOO342KAg==";
//    private final static String TENANT_ID = "f8cdef31-a31e-4b4a-93e4-5f571e91255a";
//    private final static String SECRET_VALUE = "hmI8Q~lFOpUdrgm3Jvluum9Xg_ZJz7~ApdEG6aFj";
//    private final static List<String> SCOPES = Arrays.asList("Files.Read", "Files.ReadWrite","Files.ReadWrite.AppFolder");
    private final static String REFRESH_TOKEN = "M.R3_BL2.-CXaNtB7DNmI!8AZflYqWxl6HXnGTDlgPlKdrkLoS10nAZpOC6RmjtUnCUDcLqFzqcQgVwG2gfxApKGsrd3pGvJw2WKAjLQ5BDt*mUXxurpLPbMuvkNYtmIMQ60WkbMMq9kxdiwS!ikt2pQhDux80mmVZFTIZYcJyLuPceEe*4!hq*zOye04U9u4bOyh4wisjN!FIniGbTtq5L5wGdcaiit2AYYr903FSyQe2ABBx2EYQ4JzRW4om5K3TIyEwtNT2sHAtEFmXrV5UPjVJZzvxee!IrQ5XClgI1B42TtqgLnxKS4P1gtiO3ItR4m0Rj3VvTMzsl5PM44AsXQEyLUw4KG2uHR4ruIdYf9TdC6kr6nDXgDdK2xBLKXWhboA4D*x7TQFcIyZ!UmSj*8bIt1yUbo0$";
    private final static  String SCOPE = "https://graph.microsoft.com/Files.ReadWrite.All";
    private final static  String REDIRECT_URI = "https://login.microsoftonline.com/common/oauth2/nativeclient";
    private final static  String GRANT_TYPE = "refresh_token";
    private final RestTemplate restTemplate;
    private final String accessToken;

    @Autowired
    public OneDriveService(RestTemplateBuilder restTemplateBuilder) throws IOException {
        this.restTemplate = restTemplateBuilder.build();
        String url = "https://login.microsoftonline.com/common/oauth2/v2.0/token" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();

        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", APPLICATION_ID);
        map.add("scope", SCOPE);
        map.add("redirect_uri",REDIRECT_URI);
        map.add("grant_type", GRANT_TYPE);
        map.add("refresh_token", REFRESH_TOKEN);


        HttpEntity<LinkedMultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        var response  = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(response.getBody());
            accessToken = (String) new JSONObject(response.getBody()).get("access_token");
        } else {
            throw new IOException("Cannot obtain access token");
        }

    }
//    public byte[] getRecruitmentFormPDF(String fileId) {
//        String url = "https://graph.microsoft.com/v1.0/me/drive/items/" + fileId + "/content" ;
//        System.out.println(url);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer "+accessToken);
//        HttpEntity request = new HttpEntity(headers);
//
//        var response  = restTemplate.exchange(url, HttpMethod.GET, request, byte[].class);
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return Objects.requireNonNull(response.getBody());
//        } else {
//            return null;
//        }
//    }

    public byte[] getRecruitmentDocumentFromId(String fileId) throws RecruitmentFormNotFoundException {
        String url = "https://graph.microsoft.com/v1.0/me/drive/items/" + fileId + "/content" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity request = new HttpEntity(headers);

        var response  = restTemplate.exchange(url, HttpMethod.GET, request, byte[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(response.getBody());
        } else {
            return null;
        }
    }

    public byte[] deleteRecruitmentDocumentFromId(String fileId) throws RecruitmentFormNotFoundException {
        String url = "https://graph.microsoft.com/v1.0/me/drive/items/" + fileId;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity request = new HttpEntity(headers);

        var response  = restTemplate.exchange(url, HttpMethod.DELETE, request, byte[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(response.getBody());
        } else {
            return null;
        }
    }

    public boolean deleteRecruitmentObjFromPath(String filePath) throws RecruitmentFormNotFoundException {
        String url = "https://graph.microsoft.com/v1.0/me/drive/items/" + getRecruitmentObjIdFromPath(filePath);
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity request = new HttpEntity(headers);

        var response  = restTemplate.exchange(url, HttpMethod.DELETE, request, byte[].class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    public String getRecruitmentObjIdFromPath(String filePath) throws RecruitmentFormNotFoundException {
        String url = "https://graph.microsoft.com/v1.0/me/drive/root:/" + filePath;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity request = new HttpEntity(headers);

        var response  = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("GET ID");
            System.out.println(response);
            System.out.println(response.getBody());
            return (String) new JSONObject(response.getBody()).get("id");
        } else {
            return null;
        }
    }

    public byte[] getRecruitmentDocumentFromPath(String filePath) throws RecruitmentFormNotFoundException {
        if(filePath.startsWith("/")){
            filePath = filePath.substring(1);
        }
        String url = "https://graph.microsoft.com/v1.0/me/drive/root:/" + filePath + ":/content" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity request = new HttpEntity(headers);

        var response  = restTemplate.exchange(url, HttpMethod.GET, request, byte[].class);
        System.out.println(response);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(response.getBody());
        } else {
            return null;
        }
    }

    public List<String> getChildrenList(String folderPath) throws RecruitmentFormNotFoundException {
        String url = "https://graph.microsoft.com/v1.0/me/drive/root:/" + folderPath + ":/children" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity request = new HttpEntity(headers);

        var response  = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(new JSONObject(response.getBody()));
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<String> returnList = new ArrayList<>();
            ((JSONArray) new JSONObject(response.getBody()).get("value"))
                    .forEach(val -> returnList.add(((JSONObject)val).get("name").toString()));
            return  returnList;
        } else {
            return null;
        }
    }

    public List<String> createChild(String folderPath, String folderName) throws RecruitmentFormNotFoundException {
        String url = "https://graph.microsoft.com/v1.0/me/drive/items/" + getRecruitmentObjIdFromPath(folderPath) + "/children" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("name", folderName);
        map.put("folder", new HashMap<String,String>());
        map.put("@microsoft.graph.conflictBehavior", "rename");

        HttpEntity<LinkedHashMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        System.out.println(requestEntity);
        RestTemplate restTemplate = new RestTemplate();
        var response  = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.println(response.getBody());
        if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {

            return getChildrenList(folderPath);
        } else {
            return null;
        }
    }

    public PostFileDTO postRecruitmentDocument(String filePath, byte[] pdf) {
        String url = "https://graph.microsoft.com/v1.0/me/drive/root:/" + filePath + ":/content" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdf.length);

        LinkedMultiValueMap<String, byte[]> map = new LinkedMultiValueMap<>();
        map.add("file", pdf);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(pdf, headers);
        RestTemplate restTemplate = new RestTemplate();
        var response  = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        System.out.println(response.getBody());
        if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {

            return new PostFileDTO((String) new JSONObject(response.getBody()).get("webUrl"),
                    filePath,
                    (String) new JSONObject(response.getBody()).get("id"));
        } else {
            return null;
        }
    }
    public byte[] putRecruitmentDocument(String fileId, byte[] pdf) {
        String url = "https://graph.microsoft.com/v1.0/me/drive/items/" + fileId + "/content" ;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
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
}
