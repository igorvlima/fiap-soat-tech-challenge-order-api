package com.example.fiapsoattechchallengeorderapi.utils.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.product.response.ProductDTO;
import com.example.fiapsoattechchallengeorderapi.domain.product.Product;
import com.example.fiapsoattechchallengeorderapi.utils.mappers.ProductMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductMapperTest {

    @Test
    void shouldMapDTOToDomainSuccessfully() {
        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Product Name");
        dto.setPrice(BigDecimal.valueOf(99.99));
        dto.setDescription("Product Description");
        dto.setCategory("Category");

        ProductMapper mapper = new ProductMapper();
        Product product = mapper.DTOtoDomain(dto);

        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getName(), product.getName());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getCategory(), product.getCategory());
    }

    @Test
    void shouldReturnNullWhenDTOIsNull() {
        ProductMapper mapper = new ProductMapper();
        Product product = mapper.DTOtoDomain(null);

        assertNull(product);
    }
}