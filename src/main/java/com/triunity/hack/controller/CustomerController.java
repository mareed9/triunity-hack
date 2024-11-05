package com.triunity.hack.controller;

import com.triunity.hack.model.Customer;
import com.triunity.hack.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("triunity/api/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController{
    private final CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllRegistratedCustomers(){
        return customerRepository.findAll();
    }
}
