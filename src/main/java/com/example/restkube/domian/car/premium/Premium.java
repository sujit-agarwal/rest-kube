package com.example.restkube.domian.car.premium;

import lombok.*;

import java.math.BigDecimal;

/**
 * Premium holder for coverages with only one premium
 */
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Premium {

    private final PremiumFranchise franchise;
    private final PremiumCoverage coverage;
    private final BigDecimal premiumAmount;
    private final boolean offered;

}
