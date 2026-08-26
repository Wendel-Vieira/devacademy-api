package com.dio.devacademy;

import com.dio.devacademy.controller.exception.BusinessException;
import com.dio.devacademy.controller.exception.ResourceNotFoundException;
import com.dio.devacademy.domain.model.Developer;
import com.dio.devacademy.domain.model.Plan;
import com.dio.devacademy.domain.model.Skill;
import com.dio.devacademy.domain.model.Wallet;
import com.dio.devacademy.service.DeveloperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DeveloperServiceIntegrationTest {

    @Autowired
    private DeveloperService developerService;

    private Developer createSampleDeveloper(String name, String email, String github) {
        Developer dev = new Developer();
        dev.setName(name);
        dev.setEmail(email);
        dev.setGithubUsername(github);

        Plan plan = new Plan();
        plan.setName("Pro Developer");
        plan.setPrice(new BigDecimal("49.90"));
        plan.setMentorshipCredits(2);
        plan.setActive(true);
        dev.setPlan(plan);

        Wallet wallet = new Wallet();
        wallet.setDevCoins(new BigDecimal("150.00"));
        wallet.setXpPoints(1200);
        wallet.setRankLevel("PLENO");
        dev.setWallet(wallet);

        Skill skill = new Skill();
        skill.setTitle("Java 17 & Spring Boot 3");
        skill.setDescription("Construção de APIs REST robustas e seguras");
        skill.setIconUrl("https://example.com/icons/java.png");
        skill.setCategory("Backend");
        skill.setLevel(4);
        dev.setSkills(List.of(skill));

        return dev;
    }

    @Test
    void shouldCreateAndFindDeveloperSuccessfully() {
        Developer dev = createSampleDeveloper("Lucas Silva", "lucas@example.com", "lucassilva");
        Developer created = developerService.create(dev);

        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals("Lucas Silva", created.getName());
        Assertions.assertEquals("lucas@example.com", created.getEmail());

        Developer found = developerService.findById(created.getId());
        Assertions.assertEquals(created.getId(), found.getId());
        Assertions.assertEquals("Pro Developer", found.getPlan().getName());
        Assertions.assertEquals(1, found.getSkills().size());
    }

    @Test
    void shouldThrowExceptionWhenCreatingDuplicateEmail() {
        Developer dev1 = createSampleDeveloper("Ana Costa", "ana@example.com", "anacosta");
        developerService.create(dev1);

        Developer dev2 = createSampleDeveloper("Outra Ana", "ana@example.com", "ana_github2");
        Assertions.assertThrows(BusinessException.class, () -> developerService.create(dev2));
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> developerService.findById(999L));
    }

    @Test
    void shouldDeleteDeveloperSuccessfully() {
        Developer dev = createSampleDeveloper("Carlos Souza", "carlos@example.com", "carlossouza");
        Developer created = developerService.create(dev);

        developerService.delete(created.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> developerService.findById(created.getId()));
    }
}
