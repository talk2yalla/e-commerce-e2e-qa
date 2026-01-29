package com.ecom.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductE2ETest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = TestConfig.get("baseUrl");
    }

    @Test
    public void create_product_should_persist_in_db() throws Exception {

        String sku = "SKU-" + System.currentTimeMillis();

        var resp =
                given()
                        .contentType(ContentType.JSON)
                        .body(Map.of(
                                "sku", sku,
                                "name", "Test Product",
                                "description", "From automation",
                                "price", 10.50,
                                "stock", 25
                        ))
                        .when()
                        .post("/api/v1/products")
                        .then()
                        .statusCode(201)
                        .extract().response();

        Long id = resp.jsonPath().getLong("id");
        assertThat(id).isNotNull();
        assertThat(resp.jsonPath().getString("sku")).isEqualTo(sku);

        // DB validation
        try (Connection con = DbUtils.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "SELECT id, sku, name, price, stock, status FROM products WHERE sku = ?"
            );
            ps.setString(1, sku);

            ResultSet rs = ps.executeQuery();
            assertThat(rs.next()).isTrue();

            assertThat(rs.getLong("id")).isEqualTo(id);
            assertThat(rs.getString("sku")).isEqualTo(sku);
            assertThat(rs.getString("name")).isEqualTo("Test Product");
            assertThat(rs.getInt("stock")).isEqualTo(25);
            assertThat(rs.getString("status")).isEqualTo("ACTIVE");
        }
    }
}
