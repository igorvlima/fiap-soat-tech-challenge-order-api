package com.example.fiapsoattechchallengeorderapi.application.service;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.product.ProductFeignClient;
import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.product.response.ProductDTO;
import com.example.fiapsoattechchallengeorderapi.domain.product.Product;
import com.example.fiapsoattechchallengeorderapi.exception.ProductNotFoundException;
import com.example.fiapsoattechchallengeorderapi.utils.mappers.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductFeignClient productFeignClient;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setPrice(BigDecimal.valueOf(10.0));

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(10.0));
    }

    @Test
    void fetchProductById_ShouldReturnProduct_WhenProductExists() {
        when(productFeignClient.getProductById(anyLong())).thenReturn(productDTO);
        when(productMapper.DTOtoDomain(any(ProductDTO.class))).thenReturn(product);

        Product result = productService.fetchProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(BigDecimal.valueOf(10.0), result.getPrice());

        verify(productFeignClient).getProductById(1L);
        verify(productMapper).DTOtoDomain(productDTO);
    }

    @Test
    void fetchProductById_ShouldThrowException_WhenProductNotFound() {
        when(productFeignClient.getProductById(anyLong())).thenReturn(null);

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.fetchProductById(1L)
        );

        assertEquals("Produto com ID 1 não encontrado.", exception.getMessage());
        verify(productFeignClient).getProductById(1L);
        verify(productMapper, never()).DTOtoDomain(any(ProductDTO.class));
    }
}