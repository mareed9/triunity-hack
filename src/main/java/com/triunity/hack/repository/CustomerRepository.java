package com.triunity.hack.repository;

import com.triunity.hack.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByEmail(String email);
}
