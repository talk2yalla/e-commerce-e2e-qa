package com.ecom.client;

import com.ecom.constants.ApiEndpoints;
import com.ecom.dto.CreateProductRequest;
import com.ecom.dto.ProductResponse;
import io.restassured.specification.RequestSpecification;

public class ProductClient extends BaseClient {

    public ProductClient(RequestSpecification spec) {
        super(spec);
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        return request()
                .body(request)
                .when()
                .post(ApiEndpoints.PRODUCTS)
                .then()
                .statusCode(201)
                .extract()
                .as(ProductResponse.class);
    }

    public ProductResponse getProduct(Long productId) {
        return request()
                .when()
                .get(ApiEndpoints.PRODUCTS + "/" + productId)
                .then()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);
    }
}
