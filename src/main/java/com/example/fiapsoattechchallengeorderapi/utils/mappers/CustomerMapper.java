package com.example.fiapsoattechchallengeorderapi.utils.mappers;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.customer.response.CustomerDTO;
import com.example.fiapsoattechchallengeorderapi.domain.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer DTOtoDomain(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setCpf(dto.getCpf());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setActive(dto.getActive());
        customer.setCreatedAt(dto.getCreatedAt());
        customer.setUpdatedAt(dto.getUpdatedAt());
        return customer;
    }
}
