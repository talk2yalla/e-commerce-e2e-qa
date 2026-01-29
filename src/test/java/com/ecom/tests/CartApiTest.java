package com.ecom.tests;

import com.ecom.base.BaseTest;
import com.ecom.client.*;
import com.ecom.dto.*;
import com.ecom.factory.TestDataFactory;
import com.ecom.utils.FakerUtil;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CartApiTest{

    @Test
    public void register_user_create_product_add_to_cart_should_succeed() {

        var spec = SpecFactory.defaultSpec();

        var userClient = new UserClient(spec);
        var productClient = new ProductClient(spec);
        var cartClient = new CartClient(spec);

        // 1) User
        var user = userClient.register(TestDataFactory.newUser());
        Long userId = user.getId();
        assertNotNull(userId);

        // 2) Product
        var product = productClient.createProduct(TestDataFactory.newProduct());
        Long productId = product.getId();
        assertNotNull(productId);

        // 3) Cart
        var cart = cartClient.addItem(
                TestDataFactory.addToCart(userId, productId)
        );

        assertNotNull(cart.getCartId());
        assertEquals(cart.getUserId(), userId);

        var item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow();

        assertTrue(item.getQuantity() > 0);
        assertNotNull(cart.getCartTotal());
    }

}
