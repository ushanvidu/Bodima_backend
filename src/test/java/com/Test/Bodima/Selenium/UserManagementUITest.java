package com.Test.Bodima.Api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserManagementUITest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    @DisplayName("ADD USER - Create new user via API")
    public void testAddUser() {
        String json = """
        {
            "name": "Test User", 
            "email": "test@example.com",
            "phone": "0771234567"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/api/users")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test User"))
                .body("email", equalTo("test@example.com"));
    }

    @Test
    @DisplayName("SIMPLE DELETE TEST")
    public void testSimpleDelete() {
        // 1. Create a user
        String json = "{\"name\":\"Test User\",\"email\":\"test@example.com\",\"phone\":\"0771234567\"}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .post("/api/users")
                .then()
                .statusCode(200);

        // 2. Get all users and find the last one
        Response response = given().get("/api/users");
        List<Integer> userIds = response.jsonPath().getList("userId");

        if (!userIds.isEmpty()) {
            int lastUserId = userIds.get(userIds.size() - 1);

            // 3. Delete the user
            given()
                    .delete("/api/users/" + lastUserId)
                    .then()
                    .statusCode(200);

            // 4. Verify deletion
            given()
                    .get("/api/users/" + lastUserId)
                    .then()
                    .statusCode(404);
        }
    }
    @Test
    @DisplayName("GET ALL USERS - Retrieve all users")
    public void testGetAllUsers() {
        given()
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(0)));
    }


}