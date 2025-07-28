package com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.product;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.product.response.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-api", url = "${product.api.url}")
public interface ProductFeignClient {
    @GetMapping("/product/{id}")
    ProductDTO getProductById(@PathVariable Long id);
}