package com.example.twitterclonebe;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class TwitterCloneBeApplication {

    @Value("${cloudinary.cloud-name}")
    String cloudName;
    @Value("${cloudinary.api-key}")
    String apiKey;
    @Value("${cloudinary.api-secret}")
    String apiSecret;

    public static void main(String[] args) {
        SpringApplication.run(TwitterCloneBeApplication.class, args);
    }

    @RequestMapping("")
    public String helloWorld(){
        return "Hello World!";
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Cloudinary cloudinaryConfig() {

        Cloudinary cloudinary;
        Map config = new HashMap();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }

}