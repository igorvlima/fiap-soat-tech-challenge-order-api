package com.example.fiapsoattechchallengeorderapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "local|dev|test")
class FiapSoatTechChallengeOrderApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
