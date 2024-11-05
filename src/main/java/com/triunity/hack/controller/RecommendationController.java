package com.triunity.hack.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.triunity.hack.model.Customer;
import com.triunity.hack.model.MobileDevice;
import com.triunity.hack.model.TariffPlan;
import com.triunity.hack.repository.CustomerRepository;
import com.triunity.hack.repository.MobileDeviceRepository;
import com.triunity.hack.repository.TariffPlanRepository;
import com.triunity.hack.request.RecommendationRequest;
import com.triunity.hack.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/triunity/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController{
    private final CustomerRepository customerRepository;
    private final MobileDeviceRepository mobileDeviceRepository;
    private final TariffPlanRepository tariffPlanRepository;
    private final OpenAIService openAIService;

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerRecommendation(@PathVariable Long customerId) throws JsonProcessingException{
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if(customerOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Customer customer = customerOpt.get();


        Optional<TariffPlan> tariffPlanOpt=tariffPlanRepository.findById(customer.getCurrentPackageId());
        if(tariffPlanOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        TariffPlan tariffPlan = tariffPlanOpt.get();

        List<MobileDevice> phones=mobileDeviceRepository.findAll();

        RecommendationRequest request = new RecommendationRequest(customer,tariffPlan,phones);
        String prompt = generatePhoneRecommendation(request);
        String recommendation = openAIService.getRecommendationFromGPT(prompt);

        return ResponseEntity.ok(recommendation);
    }

    private String generatePhoneRecommendation(RecommendationRequest request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("User details:\n");
        prompt.append("Full name: ").append(request.getCustomer().getFullName()).append("\n");
        prompt.append("Date of birth: ").append(request.getCustomer().getDateOfBirth()).append("\n");
        prompt.append("Gender: ").append(request.getCustomer().getGender()).append("\n");
        prompt.append("Address: ").append(request.getCustomer().getAddress()).append("\n");
        prompt.append("Monthly data: ").append(request.getTariffPlan().getMonthlyDataLimit()).append(" MB\n");
        prompt.append("Digital plugin: ").append(request.getTariffPlan().getDigitalPlugin()).append("\n");
        prompt.append("Roaming network: ").append(request.getTariffPlan().getRoamingNetwork()).append("\n");
        prompt.append("Roaming minutes: ").append(request.getTariffPlan().getRoamingMinutes()).append("\n");
        prompt.append("Budget: ").append(request.getCustomer().getPreferredBudget()).append(" RSD\n\n");

        prompt.append("Available phones:\n");
        for (MobileDevice phone : request.getPhones()) {
            prompt.append(phone.getBrand()).append(" ").append(phone.getModel())
                    .append(", Battery: ").append(phone.getBatteryCapacity()).append(" mAh").append("\n")
                    .append(", Camera: ").append(phone.getCameraResolution()).append(" MP").append("\n")
                    .append(", Display: ").append(phone.getScreenSize()).append(" MP").append("\n")
                    .append(", Memory: ").append(phone.getMemoryCapacity()).append(" GB RAM").append("\n")
                    .append(", Storage: ").append(phone.getStorageCapacity()).append(" GB").append("\n")
                    .append(", Price: ").append(phone.getPrice()).append(" RSD\n").append("\n");
        }
        prompt.append("Recommend the best phone for the user based on their usage and budget.");

        return prompt.toString();
    }
}
