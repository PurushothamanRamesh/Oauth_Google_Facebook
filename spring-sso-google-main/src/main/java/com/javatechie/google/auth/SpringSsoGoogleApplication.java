package com.javatechie.google.auth;

import okhttp3.*;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@SpringBootApplication
@RestController
public class SpringSsoGoogleApplication {

    Logger logger;

    private final OkHttpClient client = new OkHttpClient();

    @GetMapping
    public String welcome() {
        return "Welcome to Google !!";
    }

    @PostMapping
    public String postmethod(){
        logger.info("Post Mapping Success");
        return "Post Mapping Successfully ";
    }

    @PostMapping("/verify")
    public String verify(@org.springframework.web.bind.annotation.RequestBody VerificationRequest verificationRequest) {

        System.out.println("Api call successfull");
        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = "{\"consent\":true," +
                "\"reference_id\":\"" + verificationRequest.getReferenceId() + "\"," +
                "\"purpose\":\"" + verificationRequest.getPurpose() + "\"," +
                "\"aadhaar_number\":\"" + verificationRequest.getAadhaarNumber() + "\"}";

        RequestBody body = RequestBody.create(jsonBody, mediaType);

        Request request = new Request.Builder()
                .url("https://in.staging.decentro.tech/v2/kyc/aadhaar/otp")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("client_id", "KambaaIncorporation_1_sop")
                .addHeader("client_secret", "8143a0c27af7436bbbb6a81e0342967d")
                .addHeader("module_secret", "01641045e7184f7c9726347de5766a3e")
                .addHeader("content-type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSsoGoogleApplication.class, args);
    }

}
