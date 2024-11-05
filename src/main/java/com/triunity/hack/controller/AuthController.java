package com.triunity.hack.controller;

import com.triunity.hack.model.Customer;
import com.triunity.hack.model.TariffPlan;
import com.triunity.hack.repository.CustomerRepository;
import com.triunity.hack.repository.TariffPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("triunity/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController{
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final CustomerRepository customerRepository;
    private final TariffPlanRepository tariffPlanRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password){
        email = email.replace("\"", "").trim();
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null){
            logger.info("Login failed - user not found for email: " + email);
            return new ResponseEntity<>("Login failed - user not found", HttpStatus.UNAUTHORIZED);
        }
        logger.info("Found user: " + customer.getFullName() + ", checking password...");

        password = password.replace("\"", "").trim();
        if(passwordEncoder.matches(password, customer.getPassword())){
            logger.info("Login successful for user: " + customer.getFullName());
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }else{
            logger.info("Login failed - password mismatch for user: " + email);
            return new ResponseEntity<>("Login failed - invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            return new ResponseEntity<>("Customer already registered", HttpStatus.BAD_REQUEST);
        }

        //TariffPlan currentPackage = customer.getCurrentPackageId();
        //if (currentPackage != null && currentPackage.getId() == null) {
          //  tariffPlanRepository.save(currentPackage);
        //}

        if (customer.getFullName() == null || customer.getEmail() == null || customer.getPassword() == null) {
            return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);
        logger.info("Registering customer: " + customer);
        customerRepository.save(customer);
        logger.info("Customer registered successfully");
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}
