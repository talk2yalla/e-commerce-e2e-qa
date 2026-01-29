package com.ecom.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddToCartRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
