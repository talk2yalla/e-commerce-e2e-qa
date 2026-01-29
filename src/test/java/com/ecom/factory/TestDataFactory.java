package com.ecom.factory;

import com.ecom.dto.AddToCartRequest;
import com.ecom.dto.CreateProductRequest;
import com.ecom.dto.RegisterUserRequest;
import com.ecom.utils.FakerUtil;

public final class TestDataFactory {

    private TestDataFactory() {}

    /* =========================
       USER
       ========================= */
    public static RegisterUserRequest newUser() {
        return RegisterUserRequest.builder()
                .email(FakerUtil.randomEmail())
                .password("Password@123")   // stable for all tests
                .fullName(FakerUtil.fullName())
                .phone(FakerUtil.randomPhone())
                .build();
    }

    /* =========================
       PRODUCT
       ========================= */
    public static CreateProductRequest newProduct() {
        return CreateProductRequest.builder()
                .sku(FakerUtil.randomSku())
                .name(FakerUtil.productName())
                .description(FakerUtil.productDesc())
                .price(FakerUtil.randomPrice())
                .stock(FakerUtil.randomStock())
                .build();
    }

    /* =========================
       CART
       ========================= */
    public static AddToCartRequest addToCart(Long userId, Long productId) {
        return AddToCartRequest.builder()
                .userId(userId)
                .productId(productId)
                .quantity(FakerUtil.randomQuantity())
                .build();
    }
}
