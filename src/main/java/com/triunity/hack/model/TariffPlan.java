package com.triunity.hack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffPlan{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tpName; //tariff plan name
    @NotNull
    private Long packageId; //tariff plan name
    private int monthlyDataLimit; //MB
    private int monthlyMinutes; //minutes
    private int monthlySms;
    private String digitalPlugin; //digital plugins - separated by ","
    private int roamingNetwork; //MB
    private int roamingMinutes; //minutes
    private double monthlyFee; //rsd
}
