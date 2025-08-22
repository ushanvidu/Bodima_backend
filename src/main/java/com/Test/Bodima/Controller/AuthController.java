package com.Test.Bodima.Controller;

import com.Test.Bodima.Model.Login;
import com.Test.Bodima.Model.User;
import com.Test.Bodima.Service.LoginService;
import com.Test.Bodima.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final LoginService loginService;
    private final UserService userService;

    public AuthController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request, Object response) {
        try {
            String username = request.get("name");
            String password = request.get("password");
            String email = request.get("email");
            String phone = request.get("phone");

            if (username == null || password == null  || email == null || phone == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Username, password, name, email, and phone are required");
                return ResponseEntity.badRequest().body(error);
            }
            Optional<Login> loginOptional = loginService.getLoginByUsername(username);
            if (loginOptional.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Username is already taken");
            }
            User newuser =new User();
            newuser.setName(username);
            newuser.setPassword(password);
            newuser.setUserId(newuser.getUserId());
            User savedUser = userService.createUser(newuser);

            return ResponseEntity.ok(response);


        }catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Registration failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");


            if (username == null || password == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Username and password are required");
                return ResponseEntity.badRequest().body(error);
            }

            // Find login by username
            Optional<Login> loginOptional = loginService.getLoginByUsername(username);
            if (loginOptional.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid username or password");
                return ResponseEntity.badRequest().body(error);
            }

            Login login = loginOptional.get();

            // Check password (in production, use proper password hashing)
            if (!password.equals(login.getPassword())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid username or password");
                return ResponseEntity.badRequest().body(error);
            }

            // Get user details
            User user = login.getUser();

            // Return success response with user details
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("userId", user.getUserId());
            response.put("username", login.getUsername());
            response.put("userName", user.getName());
            response.put("email", user.getEmail());
            response.put("phone", user.getPhone());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Login failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Integer userId) {
        try {
            Optional<User> userOptional = userService.getUserById(userId);
            if (userOptional.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "User not found");
                return ResponseEntity.notFound().build();
            }

            User user = userOptional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("phone", user.getPhone());
            response.put("joinDate", user.getJoinDate());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get profile: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String currentPassword = request.get("currentPassword");
            String newPassword = request.get("newPassword");

            // Validate required fields
            if (username == null || currentPassword == null || newPassword == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Username, current password, and new password are required");
                return ResponseEntity.badRequest().body(error);
            }

            // Find login by username
            Optional<Login> loginOptional = loginService.getLoginByUsername(username);
            if (loginOptional.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "User not found");
                return ResponseEntity.badRequest().body(error);
            }

            Login login = loginOptional.get();


            if (!currentPassword.equals(login.getPassword())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Current password is incorrect");
                return ResponseEntity.badRequest().body(error);
            }

            // Update password
            login.setPassword(newPassword); // In production, hash this password
            loginService.updateLogin(login.getLoginId(), login);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Password changed successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to change password: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/validate-username")
    public ResponseEntity<?> validateUsername(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");

            if (username == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Username is required");
                return ResponseEntity.badRequest().body(error);
            }

            Optional<Login> loginOptional = loginService.getLoginByUsername(username);
            Map<String, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("available", loginOptional.isEmpty());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Validation failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
