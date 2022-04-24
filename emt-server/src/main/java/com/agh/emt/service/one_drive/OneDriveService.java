package com.agh.emt.service.one_drive;

import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.DriveItemUploadableProperties;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.DriveCollectionPage;
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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
public class OneDriveService {
    private static GraphServiceClient<Request> graphClient;
    private static TokenCredentialAuthProvider authProvider;

    private final static String APPLICATION_ID = "f54c6665-00ac-4b6d-96c1-952c5781547c";
    private final static String TENANT_ID = "f8cdef31-a31e-4b4a-93e4-5f571e91255a";
    private final static String SECRET_VALUE = "hmI8Q~lFOpUdrgm3Jvluum9Xg_ZJz7~ApdEG6aFj";
    private final static List<String> SCOPES = Arrays.asList("Files.Read", "Files.ReadWrite","Files.ReadWrite.AppFolder");

    private final String accessToken;

    @Autowired
    public OneDriveService(){
        final ClientSecretCredential credential = new ClientSecretCredentialBuilder()
        .clientId(APPLICATION_ID)
//        .tenantId(TENANT_ID)
        .tenantId("common")
        .clientSecret(SECRET_VALUE)
        .build();
        authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),credential);

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
//        DriveItem driveItem = graphClient.me()
//                .drive()
//                .root()
//                .itemWithPath(filePath)
//                .buildRequest()
//                .get();
        InputStream stream = graphClient.customRequest("/users/" + "erasmus_managment_tool@outlook.com" + "/drive/root/" + filePath + "/content", InputStream.class)
                .buildRequest()
                .get();
        try {
            assert stream != null;
            return stream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
//        assert driveItem != null;
//        return driveItem.f;
    }
    public byte[] postRecruitmentFormPDF(String filePath, byte[] pdf) throws RecruitmentFormNotFoundException {
        // GET /me/drive
//        User user = graphClient
////                .me()
//                .users("bd528c3adc1333af")
//                .buildRequest()
//                .get();
        DriveItem driveItem = graphClient
//                .users("bd528c3adc1333af")
                .me()
                .drive()
                .root()
                .itemWithPath(filePath)
                .content()
                .buildRequest()
                .put(pdf);

        System.out.println(driveItem.webUrl);
//        assert driveItem != null;
        return pdf;

//        String itemId = "01BQHXQL5GQVAGCFJLYRH3EAG2YHGERMQA"; //Test upload folder
//
//        IProgressCallback<DriveItem> callback = new IProgressCallback<DriveItem> () {
//            @Override
//            public void progress(final long current, final long max) {
//                //Check progress
//            }
//            @Override
//            public void success(final DriveItem result) {
//                //Handle the successful response
//                String finishedItemId = result.id;
//            }
//
//            @Override
//            public void failure(final ClientException ex) {
//            }
//        };
//
//        UploadSession uploadSession = testBase
//                .graphClient
//                .me()
//                .drive()
//                .items(itemId)
//                .itemWithPath("_hamilton.jpg")
//                .createUploadSession(new DriveItemUploadableProperties())
//                .buildRequest()
//                .post();
//
//        ChunkedUploadProvider<DriveItem> chunkedUploadProvider = new ChunkedUploadProvider<DriveItem>(
//                uploadSession,
//                testBase.graphClient,
//                uploadFile,
//                fileSize,
//                DriveItem.class);
//
//        chunkedUploadProvider.upload(callback);

//        return getMock();
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
