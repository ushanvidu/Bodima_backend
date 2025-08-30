package com.Test.Bodima.StepDefinitions;

import com.Test.Bodima.Model.User;
import com.Test.Bodima.Service.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserStepDefinitions {

    @Autowired
    private UserService userService;

    private User user;
    private User savedUser;
    private Exception exception;

    /** ---------------- ADD USER ---------------- **/
    @Given("I have a user with name {string}, email {string}, and phone {string}")
    public void i_have_a_user_with_name_email_and_phone(String name, String email, String phone) {
        user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
    }

    @When("I request to add this user")
    public void i_request_to_add_this_user() {
        try {
            savedUser = userService.createUser(user);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the user should be saved with a system-generated ID")
    public void the_user_should_be_saved_with_a_system_generated_id() {
        assertNull(exception, "An exception was thrown: " + exception);
        assertNotNull(savedUser.getUserId(), "User ID should be generated");
        assertEquals(user.getName(), savedUser.getName(), "User name should match");
        assertEquals(user.getEmail(), savedUser.getEmail(), "User email should match");
    }

    /** ---------------- DELETE USER ---------------- **/
    @Given("a user exists in the system")
    public void a_user_exists_in_the_system() {
        // Create a test user first
        User existingUser = new User();
        existingUser.setName("Ushan");
        existingUser.setEmail("ushanviduranga123@gmail.com");
        existingUser.setPhone("0715367306");
        savedUser = userService.createUser(existingUser);
    }

    @When("I request to delete that user")
    public void i_request_to_delete_that_user() {
        try {
            userService.deleteUser(savedUser.getUserId());  // Delete using the actual ID
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the user should be removed from the system")
    public void the_user_should_be_removed_from_the_system() {
        assertNull(exception, "An exception was thrown during delete: " + exception);

        // Check if the user still exists
        Optional<User> deletedUser = userService.getUserById(savedUser.getUserId());
        assertTrue(deletedUser.isEmpty(), "The user was not deleted");
    }
}
