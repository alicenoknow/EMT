package com.agh.emt.service.one_drive;

import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.azure.identity.DeviceCodeCredential;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class OneDriveService {
    private static GraphServiceClient<Request> graphClient = null;
    private static TokenCredentialAuthProvider authProvider = null;

    private final static String APPLICATION_ID = "f54c6665-00ac-4b6d-96c1-952c5781547c";
    private final static List<String> SCOPES = Arrays.asList("Files.Read","Files.Read.All", "Files.ReadWrite","Files.ReadWrite.All");

    private final String accessToken;

    @Autowired
    public OneDriveService(){
        final DeviceCodeCredential credential = new DeviceCodeCredentialBuilder()
                .clientId(APPLICATION_ID)
                .challengeConsumer(challenge -> System.out.println(challenge.getMessage()))
                .build();

        authProvider = new TokenCredentialAuthProvider(SCOPES, credential);

        DefaultLogger logger = new DefaultLogger();
        logger.setLoggingLevel(LoggerLevel.ERROR);

        graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .logger(logger)
                .buildClient();

        this.accessToken = getUserAccessToken();

        System.out.println("Access token: " + accessToken);
    }

    public static String getUserAccessToken()
    {
        try {
            URL meUrl = new URL("https://graph.microsoft.com/v1.0/me");
            return authProvider.getAuthorizationTokenAsync(meUrl).get();
        } catch(Exception ex) {
            return null;
        }
    }

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
