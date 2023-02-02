package com.example.restkube.domian.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Range;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;

import static com.example.restkube.util.Validator.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Common interface for Policy Request
 * Contains common validation rules
 */
public interface CarInsuranceRequest {

    Integer getKmPerYear();

    String getVehicleId();

    String getChassisNumber();

    String getLicenseNumber();

    BigDecimal getCatalogueValue();

    Integer getAdditionalOptionsValue();

    Integer getClaimsLastFiveYears();

    Integer getNoFaultClaimsLastFiveYears();

    Integer getYearLastClaim();

    LocalDate getEffectiveDate();

    UsedVehicle getUsedVehicle();

    BigDecimal getUserCatalogueValue();

    BigDecimal getInvoiceValue();

    LocalDate getInvoiceDate();

    Integer getConstructionYear();

    @JsonIgnore
    @AssertTrue
    default boolean isLicenseNumberOrChassisNumberOrVehicleIdValid() {
        return nonNull(getLicenseNumber()) || nonNull(getChassisNumber()) || nonNull(getVehicleId());
    }

    /**
     * AdditionalOptionsValue is mandatory for new car (when UsedVehicle is null or N)
     */
    @JsonIgnore
    @AssertTrue
    default boolean isAdditionalOptionsValid() {
        Integer value = getAdditionalOptionsValue();

        if (getUsedVehicle() != UsedVehicle.J && isNull(value)) {
            return false;
        }

        if (getUsedVehicle() != UsedVehicle.J && !CAR_ADDITIONAL_OPTIONS.contains(value)) {
            return false;
        }
        return true;
    }

    @JsonIgnore
    @AssertTrue
    default boolean isValidYearLastClaim() {
        if (nonNull(getClaimsLastFiveYears()) && getClaimsLastFiveYears() > 0) {
            return nonNull(getYearLastClaim()) &&
                    Range.closed(Year.now().minusYears(CAR_YEAR_LAST_CLAIM_MIN).getValue(), Year.now().getValue()).contains(getYearLastClaim());
        } else {
            return true;
        }
    }

    @JsonIgnore
    @AssertTrue
    default boolean isValidEffectiveDate() {
        LocalDate date = getEffectiveDate();
        if (isNull(date)) {
            return false;
        }
        return Range.closed(LocalDate.now().toEpochDay(), LocalDate.now().plusDays(CAR_EFFECTIVE_DATE_MAX).toEpochDay()).contains(date.toEpochDay());
    }

    /**
     * When usedVehicle flag is null or 'N' then userCatalogueValue is mandatory
     * and value should be in range of 5000 to 280000
     */
    @JsonIgnore
    @AssertTrue
    default boolean isUserCatalogueValueValid() {
        BigDecimal userCatalogueValue = getUserCatalogueValue();
        if ((getUsedVehicle() != UsedVehicle.J) && isNull(userCatalogueValue)) {
            return false;
        }
        Range<BigDecimal> userCatalogueValueRange = Range.closed(BigDecimal.valueOf(5000), BigDecimal.valueOf(280000));
        if ((getUsedVehicle() != UsedVehicle.J) && nonNull(userCatalogueValue) && !userCatalogueValueRange.contains(userCatalogueValue)) {
            return false;
        }
        return true;
    }

    /**
     * invoiceValue is available then invoiceDate must be available or vice versa
     */
    @JsonIgnore
    @AssertTrue
    default boolean isInvoiceDateAndInvoiceValueValid() {

        BigDecimal invoiceValue = getInvoiceValue();
        LocalDate invoiceDate = getInvoiceDate();

        if ((nonNull(invoiceValue) && isNull(invoiceDate)) || (isNull(invoiceValue) && nonNull(invoiceDate))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * invoiceDate year must be greater than or equal to constructionYear year
     */
    @JsonIgnore
    @AssertTrue
    default boolean isInvoiceDateValid() {

        if (nonNull(getInvoiceDate()) && (getInvoiceDate().getYear() < getConstructionYear().intValue())) {
            return false;
        } else {
            return true;
        }
    }

}
