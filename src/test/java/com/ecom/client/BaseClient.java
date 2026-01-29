package com.ecom.client;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {

    protected RequestSpecification spec;

    protected BaseClient(RequestSpecification spec) {
        this.spec = spec;
    }

    protected io.restassured.specification.RequestSpecification request() {
        return given()
                .spec(spec)
                .log().ifValidationFails();
    }
}
