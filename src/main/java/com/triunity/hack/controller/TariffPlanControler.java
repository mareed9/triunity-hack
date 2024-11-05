package com.triunity.hack.controller;

import com.triunity.hack.model.TariffPlan;
import com.triunity.hack.repository.TariffPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("triunity/api/tariff-plans")
@RequiredArgsConstructor
public class TariffPlanControler{
    private final TariffPlanRepository tariffPlanRepository;

    @GetMapping
    public List<TariffPlan> getAllTariffPlans(){
        return tariffPlanRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createTariffPlan(@RequestBody TariffPlan tariffPlan) {
        tariffPlanRepository.save(tariffPlan);
        return new ResponseEntity<>("Tariff plan created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTariffPlan(@PathVariable Long id, @RequestBody TariffPlan updatedTariffPlan) {
        return tariffPlanRepository.findById(id)
                .map(tariffPlan -> {
                    tariffPlan.setTpName(updatedTariffPlan.getTpName());
                    tariffPlan.setMonthlyDataLimit(updatedTariffPlan.getMonthlyDataLimit());
                    tariffPlan.setMonthlyMinutes(updatedTariffPlan.getMonthlyMinutes());
                    tariffPlan.setMonthlySms(updatedTariffPlan.getMonthlySms());
                    tariffPlan.setDigitalPlugin(updatedTariffPlan.getDigitalPlugin());
                    tariffPlan.setRoamingNetwork(updatedTariffPlan.getRoamingNetwork());
                    tariffPlan.setRoamingMinutes(updatedTariffPlan.getRoamingMinutes());
                    tariffPlan.setMonthlyFee(updatedTariffPlan.getMonthlyFee());
                    tariffPlanRepository.save(tariffPlan);
                    return new ResponseEntity<>("Tariff plan updated successfully", HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>("Tariff plan not found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTariffPlan(@PathVariable Long id) {
        if (tariffPlanRepository.existsById(id)) {
            tariffPlanRepository.deleteById(id);
            return new ResponseEntity<>("Tariff plan deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Tariff plan not found", HttpStatus.NOT_FOUND);
    }
}
