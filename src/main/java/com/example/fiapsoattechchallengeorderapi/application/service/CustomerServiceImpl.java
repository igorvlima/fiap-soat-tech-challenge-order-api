package com.example.fiapsoattechchallengeorderapi.application.service;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.customer.CustomerFeignClient;
import com.example.fiapsoattechchallengeorderapi.application.usecase.customer.CustomerUseCase;
import com.example.fiapsoattechchallengeorderapi.domain.customer.Customer;
import com.example.fiapsoattechchallengeorderapi.exception.CustomerNotFoundException;
import com.example.fiapsoattechchallengeorderapi.utils.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerUseCase {

    private final CustomerFeignClient customerFeignClient;
    private final CustomerMapper customerMapper;

    @Override
    public Customer fetchCustomerById(Long id) {
        var customer = customerFeignClient.getCustomerById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Cliente com ID " + id + " não encontrado.");
        }
        return customerMapper.DTOtoDomain(customer);
    }
}
