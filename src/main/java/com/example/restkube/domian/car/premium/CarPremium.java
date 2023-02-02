package com.example.restkube.domian.car.premium;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

/**
 * Premium for car insurance
 */
@Getter
@Builder
@AllArgsConstructor
public class CarPremium {

    private final Premium civilLiability;
    @Singular("addOmnium")
    private final List<Premium> omnium;
    private final Premium miniOmnium;
    @Singular("addPremiumProtection")
    private final List<Premium> premiumProtection;
    private final Premium driverInsurance;
    private final Premium legalAssistance;
    private final Premium breakdownAssistance;
    @Singular("addTrailer")
    private final List<Premium> trailer;

}
