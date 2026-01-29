package com.ecom.tests;

import com.ecom.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthRegisterTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = TestConfig.get("baseUrl");
    }

    @Test
    public void register_user_should_create_user_and_validate_db() throws Exception {

        String email = "api_user_" + System.currentTimeMillis() + "@test.com";

        Map<String, Object> body = Map.of(
                "email", email,
                "password", "Password@123",
                "fullName", "API User",
                "phone", "1234567890"
        );

        var response =
                given()
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post("/api/v1/auth/register")
                        .then()
                        .statusCode(201)
                        .extract().response();

        // --- API Assertions ---
        assertThat(response.jsonPath().getString("email")).isEqualTo(email);
        assertThat(response.jsonPath().getString("role")).isEqualTo("CUSTOMER");
        assertThat(response.jsonPath().getString("status")).isEqualTo("ACTIVE");

        // --- DB Assertions ---
        try (Connection con = DbUtils.getConnection();
             ResultSet rs = DbUtils.selectUserByEmail(con, email)) {

            assertThat(rs.next()).isTrue();

            assertThat(rs.getString("email")).isEqualTo(email);
            assertThat(rs.getString("full_name")).isEqualTo("API User");
            assertThat(rs.getString("phone")).isEqualTo("1234567890");
            assertThat(rs.getString("role")).isEqualTo("CUSTOMER");
            assertThat(rs.getString("status")).isEqualTo("ACTIVE");

            // hashed password must exist and should NOT equal plain password
            String passwordHash = rs.getString("password_hash");
            assertThat(passwordHash).isNotBlank();
            assertThat(passwordHash).doesNotContain("Password@123");

            // timestamps sanity
            assertThat(rs.getTimestamp("created_at")).isNotNull();
            assertThat(rs.getTimestamp("updated_at")).isNotNull();
        }
    }
}
