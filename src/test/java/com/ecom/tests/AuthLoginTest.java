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

public class AuthLoginTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = TestConfig.get("baseUrl");
    }

    @Test
    public void login_should_work_for_newly_registered_user_and_db_should_have_user() throws Exception {

        String email = "login_user_" + System.currentTimeMillis() + "@test.com";
        String password = "Password@123";

        // 1) Register
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "email", email,
                        "password", password,
                        "fullName", "Login User",
                        "phone", "9999999999"
                ))
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(201);

        // 2) DB check (user exists + password_hash present)
        try (Connection con = DbUtils.getConnection();
             ResultSet rs = DbUtils.selectUserByEmail(con, email)) {

            assertThat(rs.next()).isTrue();
            assertThat(rs.getString("email")).isEqualTo(email);
            assertThat(rs.getString("password_hash")).isNotBlank();
        }

        // 3) Login success
        var loginResp =
                given()
                        .contentType(ContentType.JSON)
                        .body(Map.of(
                                "email", email,
                                "password", password
                        ))
                        .when()
                        .post("/api/v1/auth/login")
                        .then()
                        .statusCode(200)
                        .extract().response();

        assertThat(loginResp.jsonPath().getString("email")).isEqualTo(email);
        assertThat(loginResp.jsonPath().getString("message")).containsIgnoringCase("success");
    }

    @Test
    public void login_should_fail_for_wrong_password() {

        // Use an existing user from your DB (you can also register here, but keeping it simple)
        String email = "ravi@test.com";

        var resp =
                given()
                        .contentType(ContentType.JSON)
                        .body(Map.of("email", email, "password", "WrongPassword@123"))
                        .when()
                        .post("/api/v1/auth/login")
                        .then()
                        .statusCode(401)
                        .extract().response();

        assertThat(resp.jsonPath().getInt("status")).isEqualTo(401);
        assertThat(resp.jsonPath().getString("error")).isEqualTo("Unauthorized");
        assertThat(resp.jsonPath().getString("path")).isEqualTo("/api/v1/auth/login");

    }
}
