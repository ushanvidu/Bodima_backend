
//Select 2 secarios

// 1. User Login
// 2. User Update


package com.Test.Bodima.Service;
import com.Test.Bodima.Model.User;
import com.Test.Bodima.Repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService; // UserService is injected with the mock UserRepository

    private User testUser;

    @BeforeEach
    void setUp() {
        // Arrange: Create a test user object before each test
        testUser = new User();
        testUser.setName("Ushan");
        testUser.setEmail("ushanviduranga123@gmail.com");
        testUser.setPhone("0715367306");

    }

    // TDD for ADD USER

    //Green light
    @Test
    void createUser_WithValidData_ShouldReturnSavedUser() {
        // Arrange: Mock the repository's save method
        User savedUser = new User();
        savedUser.setUserId(1);
        savedUser.setName(testUser.getName());
        savedUser.setEmail(testUser.getEmail());
        savedUser.setPhone(testUser.getPhone());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act: Call the method we are testing
        User result = userService.createUser(testUser);



        assertNotNull(result.getUserId());
        assertEquals("Ushan", result.getName());
        assertEquals("ushanviduranga123@gmail.com", result.getEmail());
        verify(userRepository, times(1)).save(testUser);
    }


    //red light for create user

    @Test

    void createUser_WithInvalidData_ShouldThrowException() {
        User savedUser = new User();
        savedUser.setUserId(1);
        savedUser.setName(testUser.getName());
        savedUser.setEmail(testUser.getEmail());
        savedUser.setPhone(testUser.getPhone());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act: Call the method we are testing
        User result = userService.createUser(testUser);

        //check with given values

        assertNotNull(result.getUserId());
        assertEquals("Ushan", result.getName());
        assertEquals("john@example.com", result.getEmail());
        verify(userRepository, times(1)).save(testUser);

    }

    // TDD for DELETE USER
    @Test
    //Green test

    void deleteUser_WithValidId_ShouldCallRepositoryDelete() {

        doNothing().when(userRepository).deleteById(1);

        // Act
        userService.deleteUser(1);

        // Assert: Verify that deleteById was called with the correct ID
        verify(userRepository, times(1)).deleteById(1);

        // Optional: Verify findById was NEVER called if you want to be explicit
        verify(userRepository, never()).findById(anyInt());
    }


    //Red test

    @Test
    void deleteUser_WithInvalidId_ShouldStillAttemptDelete() {


        doNothing().when(userRepository).deleteById(999);


        //check with wrong Id
        userService.deleteUser(999);



        verify(userRepository, times(999)).deleteById(999);

        verify(userRepository, never()).findById(anyInt());
    }
}