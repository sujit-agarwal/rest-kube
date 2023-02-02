package com.example.restkube.domian.car;

import com.example.restkube.domian.car.common.details.*;
import com.example.restkube.util.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class CarDetails {

    private final String make;
    @Size(min = 1, max = 255)
    @Pattern(regexp = Validator.VEHICLE_DATA_PATTERN)
    private final String makeCode;
    @NotNull
    private final Integer constructionYear;
    @NotNull
    private final FuelType fuelType;
    private final String model;
    @Size(min = 1, max = 255)
    @Pattern(regexp = Validator.VEHICLE_DATA_PATTERN)
    private final String modelCode;
    private final VehicleType vehicleType;
    private final Integer weight;
    private final Integer power;
    private final Integer cylinderCapacity;
    private final String type;
    @Size(min = 1, max = 255)
    @Pattern(regexp = Validator.VEHICLE_DATA_PATTERN)
    private final String typeCode;
    private final String vehicleId;
    private final String licenseNumber;
    private final String chassisNumber;
    private final BigDecimal catalogueValue;

    private final Usage carUsage;
    private final Mileage mileage;
    private final Integer claimFreeYears;
    private final String regularDriver;
    private final String additionalDriver1;
    private final String additionalDriver2;
    private final AlarmSystem alarmSystem;

    @JsonIgnore
    @AssertTrue
    public boolean isModelCodeValid() {
        if (this.modelCode == null) {
            return true;
        } else {
            return isNotBlank(this.makeCode);
        }
    }

    @JsonIgnore
    @AssertTrue
    public boolean isTypeCodeValid() {
        if (this.typeCode == null) {
            return true;
        } else {
            return isNotBlank(this.makeCode) && isNotBlank(this.modelCode);
        }
    }

    @JsonIgnore
    @AssertTrue
    public boolean isConstructionYearValid() {
        return Validator.isVehicleConstructionYearValid(constructionYear);
    }

    @JsonIgnore
    public boolean isRetrieveTypeDetails() {
        return isNotBlank(this.makeCode) && isNotBlank(this.modelCode) && isNotBlank(this.typeCode);
    }

    @JsonIgnore
    public boolean isRetrieveTypes() {
        return isNotBlank(this.makeCode) && isNotBlank(this.modelCode) && isBlank(this.typeCode);
    }

    @JsonIgnore
    public boolean isRetrieveModels() {
        return isNotBlank(this.makeCode) && isBlank(this.modelCode) && isBlank(this.typeCode);
    }

}
