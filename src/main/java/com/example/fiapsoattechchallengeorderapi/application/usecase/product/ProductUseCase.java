package com.example.fiapsoattechchallengeorderapi.application.usecase.product;

import com.example.fiapsoattechchallengeorderapi.domain.product.Product;

public interface ProductUseCase {
    Product fetchProductById(Long id);
}
