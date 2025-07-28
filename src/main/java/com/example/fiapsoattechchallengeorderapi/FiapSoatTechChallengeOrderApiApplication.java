package com.example.fiapsoattechchallengeorderapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FiapSoatTechChallengeOrderApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiapSoatTechChallengeOrderApiApplication.class, args);
    }
}
