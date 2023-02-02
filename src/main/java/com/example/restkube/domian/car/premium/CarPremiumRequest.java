package com.example.restkube.domian.car.premium;

import com.example.restkube.domian.car.CarInsuranceRequest;
import com.example.restkube.util.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class CarPremiumRequest {

    @NotNull
    private final LocalDate dateOfBirth;

    @NotNull
    @Pattern(regexp = Validator.POSTAL_CODE_PATTERN)
    private final String postalCode;

    @Pattern(regexp = Validator.RESIDENCE_NUMBER_PATTERN)
    private final String residenceNumber;

    @Pattern(regexp = Validator.STREET_NAME_PATTERN)
    private final String streetName;

    @Pattern(regexp = Validator.CITY_PATTERN)
    private final String city;

    /**
     * See {@link CarInsuranceRequest#isValidEffectiveDate} method for additional Validations
     */
    @NotNull
    private final LocalDate effectiveDate;

    @NotNull
    @Min(4)
    @Max(6)
    private final Integer kmPerYear;

    // See isLicenseNumberOrChassisNumberOrVehicleIdValid method for additional Validations
    @Pattern(regexp = Validator.ALPHA_NUMERIC_PATTERN)
    @Size(max = 20)
    private final String vehicleId;

    // See isLicenseNumberOrChassisNumberOrVehicleIdValid method for additional Validations
    @Pattern(regexp = Validator.CHASSIS_NUMBER_PATTERN)
    private final String chassisNumber;

    /**
     * See  method for additional Validations
     */
    @Digits(integer = 15, fraction = 2)
    private final BigDecimal userCatalogueValue;

    // See isLicenseNumberOrChassisNumberOrVehicleIdValid method for additional validations
    @Pattern(regexp = Validator.LICENSE_PLATE_PATTERN)
    private final String licenseNumber;

    @Singular
    @Size(max = 99)
    private final List<PremiumTheftCode> theftCodes;

    @NotNull
    @Min(0)
    @Max(4)
    private final Integer claimsLastFiveYears;

    @NotNull
    @Min(0)
    @Max(4)
    private final Integer noFaultClaimsLastFiveYears;

    private final Integer yearLastClaim;

    @NotNull
    @Min(0)
    @Max(5)
    private final Integer yearsInsuredUsualDriver;

    private final LocalDate dateOfBirthUsualDriver;
    @NotNull
    private final Boolean homeOwner;
    @NotNull
    @Digits(integer = 4, fraction = 0)
    private final Integer constructionYear;
    @NotNull
    @Min(0)
    @Max(20000)
    private final Integer weight;
    @NotNull
    @Min(0)
    @Max(20000)
    private final Integer power;
    @NotNull
    @Min(0)
    @Max(20000)
    private final Integer cylinderCapacity;
    @Digits(integer = 15, fraction = 2)
    private final BigDecimal catalogueValue;
    @Pattern(regexp = Validator.PROMO_CODE_PATTERN)
    private final String promoCode;
    @Digits(integer = 15, fraction = 2)
    private final BigDecimal userMarketValue;
    @Digits(integer = 15, fraction = 2)
    private final BigDecimal marketValue;
    @Min(1)
    @Max(157300)
    @Digits(integer = 15, fraction = 2)
    private final BigDecimal invoiceValue;

    /**
     * See {@link CarPremiumRequest#isLicenseIssueYearValid method for additional Validations
     */
    @NotNull
    private Integer licenseIssueYear;

    @JsonIgnore
    @AssertTrue
    public boolean isLicenseIssueYearValid() {
        if (licenseIssueYear != null) {
            if (dateOfBirthUsualDriver != null) {
                return Validator.isBeLicenseIssueYearValid(licenseIssueYear, dateOfBirthUsualDriver);
            } else if (dateOfBirth != null) {
                return Validator.isBeLicenseIssueYearValid(licenseIssueYear, dateOfBirth);
            }
        }
        return false;

    }

    @JsonIgnore
    @AssertTrue
    public boolean isValidDateOfBirthUsualDriver() {
        if (dateOfBirthUsualDriver == null) {
            return true;
        }
        return Validator.isBeDateOfBirthValid(dateOfBirthUsualDriver);
    }

    @JsonIgnore
    @AssertTrue
    public boolean isValidDateOfBirth() {
        if (dateOfBirth == null) {
            return true;
        }
        return Validator.isBeDateOfBirthValid(dateOfBirth);
    }

    @JsonIgnore
    @AssertTrue
    public boolean isConstructionYearValid() {
        return Validator.isVehicleConstructionYearValid(constructionYear);
    }
}
