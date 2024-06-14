package com.javatechie.google.auth;

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class VerificationController {

    private final OkHttpClient client = new OkHttpClient();


    @GetMapping
    public String welcome() {
        return "Welcome to Google !!";
    }
    @GetMapping("/verify")
    public String verify(
            @RequestParam String referenceId,
            @RequestParam String purpose,
            @RequestParam String aadhaarNumber) {

        System.out.println("Api call successfull");
        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = "{\"consent\":true," +
                "\"reference_id\":\"" + referenceId + "\"," +
                "\"purpose\":\"" + purpose + "\"," +
                "\"aadhaar_number\":\"" + aadhaarNumber + "\"}";

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
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

}
