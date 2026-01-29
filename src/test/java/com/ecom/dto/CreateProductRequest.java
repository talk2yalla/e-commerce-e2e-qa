package com.ecom.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductRequest {
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
