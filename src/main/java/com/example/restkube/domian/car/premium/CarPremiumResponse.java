package com.example.restkube.domian.car.premium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CarPremiumResponse {

    @Singular
    @Size(max = 99)
    private final List<CarPremium> premiums;
    @Singular
    @Size(max = 99)
    private final List<PremiumTheftCode> theftCodes;


}
