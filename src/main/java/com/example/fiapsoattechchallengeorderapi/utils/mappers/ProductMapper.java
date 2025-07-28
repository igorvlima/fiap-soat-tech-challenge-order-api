package com.example.fiapsoattechchallengeorderapi.utils.mappers;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.product.response.ProductDTO;
import com.example.fiapsoattechchallengeorderapi.domain.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product DTOtoDomain(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());

        return product;
    }
}
