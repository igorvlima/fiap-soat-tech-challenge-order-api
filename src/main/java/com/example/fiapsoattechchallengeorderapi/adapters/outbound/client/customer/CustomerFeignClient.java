package com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.customer;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.customer.response.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-api", url = "${customer.api.url}")
public interface CustomerFeignClient {
    @GetMapping("/customer/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id);
}
