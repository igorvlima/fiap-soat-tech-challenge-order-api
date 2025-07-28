package com.example.fiapsoattechchallengeorderapi.application.service;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.product.ProductFeignClient;
import com.example.fiapsoattechchallengeorderapi.application.usecase.product.ProductUseCase;
import com.example.fiapsoattechchallengeorderapi.domain.product.Product;
import com.example.fiapsoattechchallengeorderapi.exception.ProductNotFoundException;
import com.example.fiapsoattechchallengeorderapi.utils.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductUseCase {

    private final ProductFeignClient productFeignClient;
    private final ProductMapper productMapper;

    @Override
    public Product fetchProductById(Long id) {
        var product = productFeignClient.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException("Produto com ID " + id + " não encontrado.");
        }
        return productMapper.DTOtoDomain(product);
    }
}
