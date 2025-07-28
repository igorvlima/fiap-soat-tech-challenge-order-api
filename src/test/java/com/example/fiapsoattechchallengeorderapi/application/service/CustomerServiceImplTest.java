package com.example.fiapsoattechchallengeorderapi.application.service;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.customer.CustomerFeignClient;
import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.customer.response.CustomerDTO;
import com.example.fiapsoattechchallengeorderapi.domain.customer.Customer;
import com.example.fiapsoattechchallengeorderapi.exception.CustomerNotFoundException;
import com.example.fiapsoattechchallengeorderapi.utils.mappers.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerFeignClient customerFeignClient;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerDTO customerDTO;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setEmail("test@example.com");
        customerDTO.setName("Test Customer");

        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");
        customer.setName("Test Customer");
    }

    @Test
    void fetchCustomerById_ShouldReturnCustomer_WhenCustomerExists() {
        when(customerFeignClient.getCustomerById(anyLong())).thenReturn(customerDTO);
        when(customerMapper.DTOtoDomain(any(CustomerDTO.class))).thenReturn(customer);

        Customer result = customerService.fetchCustomerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test Customer", result.getName());

        verify(customerFeignClient).getCustomerById(1L);
        verify(customerMapper).DTOtoDomain(customerDTO);
    }

    @Test
    void fetchCustomerById_ShouldThrowException_WhenCustomerNotFound() {
        when(customerFeignClient.getCustomerById(anyLong())).thenReturn(null);

        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.fetchCustomerById(1L)
        );

        assertEquals("Cliente com ID 1 não encontrado.", exception.getMessage());
        verify(customerFeignClient).getCustomerById(1L);
        verify(customerMapper, never()).DTOtoDomain(any(CustomerDTO.class));
    }
}