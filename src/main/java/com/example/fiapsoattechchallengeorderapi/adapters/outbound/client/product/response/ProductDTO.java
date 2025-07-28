package com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.product.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String category;
}
