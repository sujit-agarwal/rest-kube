package com.example.restkube.domian.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CarDetailsList {

    @Singular
    @Size(max = 30)
    private final List<CarDetails> carDetails;

}
