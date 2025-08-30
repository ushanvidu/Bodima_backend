
//Select 2 secarios

// 1. User Login
// 2. User Update


package com.Test.Bodima.Service;

import com.Test.Bodima.Model.User;
import com.Test.Bodima.Repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Create a test user
        testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPhone("1234567890");
    }

    @Test
    void testCreateUser() {
        // When
        User savedUser = userService.createUser(testUser);

        // Then
        assertNotNull(savedUser);
        assertNotNull(savedUser.getUserId());
        assertEquals(testUser.getName(), savedUser.getName());
        assertEquals(testUser.getEmail(), savedUser.getEmail());
        assertEquals(testUser.getPhone(), savedUser.getPhone());
    }

    @Test
    void testGetAllUsers() {
        // Given
        userService.createUser(testUser);
        
        User secondUser = new User();
        secondUser.setName("Second User");
        secondUser.setEmail("second@example.com");
        secondUser.setPhone("0987654321");
        userService.createUser(secondUser);

        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        // Given
        User savedUser = userService.createUser(testUser);

        // When
        Optional<User> foundUser = userService.getUserById(savedUser.getUserId());

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getUserId(), foundUser.get().getUserId());
    }

    @Test
    void testDeleteUser() {
        // Given
        User savedUser = userService.createUser(testUser);

        // When
        userService.deleteUser(savedUser.getUserId());

        // Then
        Optional<User> deletedUser = userService.getUserById(savedUser.getUserId());
        assertFalse(deletedUser.isPresent());
    }
}