package com.javatechie.google.auth;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@SpringBootApplication
@RestController
public class SpringSsoGoogleApplication {

    Logger logger;

    @GetMapping
    public String welcome() {
        return "Welcome to Google !!";
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        System.out.println("username : " + principal.getName());
        return principal;
    }
    @GetMapping("/data")
    public OAuth2User data(@AuthenticationPrincipal OAuth2User oAuth2User){
        return oAuth2User;
    }
    @GetMapping("/data1")
    public OAuth2User data1(OAuth2User oAuth2User){
        return oAuth2User;
    }
    @GetMapping("/data2")
    public Map<String, Object> data2(OAuth2AuthenticatedPrincipal oAuth2User){
        return oAuth2User.getAttributes();
    }

    @GetMapping("/data3")
    public OAuth2ClientProperties data3(OAuth2ClientProperties properties){
        return properties;
    }
    @GetMapping("/data4")
    public OAuth2AuthenticationToken data4(OAuth2AuthenticationToken properties){
        return properties;
    }


    @PostMapping
    public String postmethod(){
        logger.info("Post Mapping Success");
        return "Post Mapping Successfully ";
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringSsoGoogleApplication.class, args);
    }

}
