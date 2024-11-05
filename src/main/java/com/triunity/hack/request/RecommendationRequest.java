package com.triunity.hack.request;

import com.triunity.hack.model.Customer;
import com.triunity.hack.model.MobileDevice;
import com.triunity.hack.model.TariffPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationRequest{
    private Customer customer;
    private TariffPlan tariffPlan;
    private List<MobileDevice> phones;
}
