package com.ecom.client;

import com.ecom.constants.ApiEndpoints;
import com.ecom.dto.RegisterUserRequest;
import com.ecom.dto.RegisterUserResponse;
import io.restassured.specification.RequestSpecification;

public class UserClient extends BaseClient {

    public UserClient(RequestSpecification spec) {
        super(spec);
    }

    public RegisterUserResponse register(RegisterUserRequest request) {
        return request()
                .body(request)
                .when()
                .post(ApiEndpoints.REGISTER)
                .then()
                .statusCode(201)
                .extract()
                .as(RegisterUserResponse.class);
    }
}
