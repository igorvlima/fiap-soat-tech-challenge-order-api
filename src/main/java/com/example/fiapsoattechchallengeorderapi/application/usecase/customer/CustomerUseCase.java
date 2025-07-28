package com.example.fiapsoattechchallengeorderapi.application.usecase.customer;

import com.example.fiapsoattechchallengeorderapi.domain.customer.Customer;

public interface CustomerUseCase {
    Customer fetchCustomerById(Long id);
}
