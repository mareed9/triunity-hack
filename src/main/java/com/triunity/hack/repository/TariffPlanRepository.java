package com.triunity.hack.repository;

import com.triunity.hack.model.TariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffPlanRepository extends JpaRepository<TariffPlan, Long>{
    TariffPlan findByPackageId(Long id);
}
