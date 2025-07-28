package com.example.fiapsoattechchallengeorderapi.utils.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.client.customer.response.CustomerDTO;
import com.example.fiapsoattechchallengeorderapi.domain.customer.Customer;
import com.example.fiapsoattechchallengeorderapi.utils.mappers.CustomerMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CustomerMapperTest {

    @Test
    void shouldMapDTOToDomainSuccessfully() {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(1L);
        dto.setCpf("12345678900");
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");
        dto.setActive(true);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());

        CustomerMapper mapper = new CustomerMapper();
        Customer customer = mapper.DTOtoDomain(dto);

        assertNotNull(customer);
        assertEquals(dto.getId(), customer.getId());
        assertEquals(dto.getCpf(), customer.getCpf());
        assertEquals(dto.getName(), customer.getName());
        assertEquals(dto.getEmail(), customer.getEmail());
        assertEquals(dto.getActive(), customer.getActive());
        assertEquals(dto.getCreatedAt(), customer.getCreatedAt());
        assertEquals(dto.getUpdatedAt(), customer.getUpdatedAt());
    }

    @Test
    void shouldReturnNullWhenDTOIsNull() {
        CustomerMapper mapper = new CustomerMapper();
        Customer customer = mapper.DTOtoDomain(null);

        assertNull(customer);
    }
}