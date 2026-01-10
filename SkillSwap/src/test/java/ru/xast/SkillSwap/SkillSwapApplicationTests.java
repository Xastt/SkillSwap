package ru.xast.SkillSwap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
    classes = SkillSwapApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class SkillSwapApplicationTests {

    @Test
    void contextLoads() {
        // Просто проверяем что тест запускается
        System.out.println("Context loading test passed!");
    }
}
