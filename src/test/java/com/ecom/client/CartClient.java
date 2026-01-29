package com.ecom.client;

import com.ecom.constants.ApiEndpoints;
import com.ecom.dto.AddToCartRequest;
import com.ecom.dto.CartResponse;
import io.restassured.specification.RequestSpecification;

public class CartClient extends BaseClient {

    public CartClient(RequestSpecification spec) {
        super(spec);
    }

    public CartResponse addItem(AddToCartRequest request) {
        return request()
                .body(request)
                .when()
                .post(ApiEndpoints.CART_ITEMS)
                .then()
                .statusCode(201)
                .extract()
                .as(CartResponse.class);
    }
}
