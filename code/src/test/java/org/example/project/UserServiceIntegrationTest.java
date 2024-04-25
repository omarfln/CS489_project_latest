package org.example.project;


import jakarta.transaction.Transactional;
import org.example.project.configuration.DataSetup;
import org.example.project.service.UserService;
import org.example.project.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional

//@SpringJUnitConfig(classes = {DataSetup.class})@ActiveProfiles("test")
public class UserServiceIntegrationTest {

    @Autowired
    private  UserServiceImpl userService;

    @Test
    void testFindUserById_ExistingId() {
        Long existingId = 1L;
        assertNotNull(userService.getUserById(existingId));
    }

    @Test
    void testFindUserById_NonExistingId() {
        Long nonExistingId = 999L;
        assertNull(userService.getUserById(nonExistingId));
    }
}
