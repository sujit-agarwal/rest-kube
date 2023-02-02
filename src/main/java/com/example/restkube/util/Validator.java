package com.example.restkube.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
public final class Validator {

    public static final String ALPHA_NUMERIC_PATTERN = "^[\\w\\s-]+$";
    public static final String POSTAL_CODE_PATTERN = "^[0-9]{4}$";
    public static final String FOREIGN_POSTAL_CODE_PATTERN = "^[A-Za-zÀ-ÿ0-9°\\.'*`´’,\\- \"()#&+/?:;_=]{1,100}$";
    public static final String CITY_PATTERN = "^[A-Za-zÀ-ÿ0-9°\\.'*`´’,\\- \"()#&+/?:;_=]{1,100}$";
    public static final String STREET_NAME_PATTERN = "^[A-Za-zÀ-ÿ0-9°\\.'*`´’,\\- \"()#&+/?:;_=]{1,100}$";
    public static final String RESIDENCE_NUMBER_PATTERN = "^[A-Za-zÀ-ÿ0-9°\\.'*`´’,\\- \"()#&+/?:;_=]{1,100}$";
    public static final String LICENSE_PLATE_PATTERN = "^(?!XA|WA|T|O)(?=.{2,10}$)[A-Z0-9]([A-Z0-9]|(-(?!-)))*[A-Z0-9]$";
    public static final String CHASSIS_NUMBER_PATTERN = "^[A-Z0-9&&[^IOQ]]{17}$";
    public static final String TRAILER_CHASSIS_NUMBER_PATTERN = "^[A-Z0-9]{1,17}$";
    public static final String FIRST_NAME_PATTERN = "^(?![\\s.]+$)[a-zA-ZÀ-ŸœŒ'`\\-\\s\\.]{1,50}$";
    public static final String LAST_NAME_PATTERN = "^(?![\\s.]+$)[a-zA-ZÀ-ŸœŒ'`\\-\\s\\.]{1,80}$";
    public static final String BE_ACCOUNT_HOLDER_NAME_PATTERN = "^(?![\\s.]+$)[a-zA-ZÀ-ŸœŒ'`\\-\\s]{1,45}$";
    public static final String AGENT_NAME_PATTERN = "^(?![\\s.]+$)[a-zA-ZÀ-ŸœŒ'`\\-\\s]{1,45}$";
    public static final String AGENCY_EMAIL_PATTERN = "^(.+)@ing.(com|be)$";
    public static final int DRIVING_LICENSE_MIN_AGE = 17;
    public static final int CAR_EFFECTIVE_DATE_MAX = 450;
    public static final int CAR_YEAR_LAST_CLAIM_MIN = 5;
    public static final List<Integer> CAR_ADDITIONAL_OPTIONS = ImmutableList.of(1500, 3000, 9999);
    public static final int DATE_OF_BIRTH_MIN_YEAR = 100;
    public static final String PROMO_CODE_PATTERN = "^[A-Z0-9]{1,15}$";
    public static final int CAR_CONSTRUCTION_YEAR_MIN = 24;
    public static final String POLICY_ID = "^\\d{2,15}$";
    public static final String LOCATION_ID_PATTERN = "^[0-9]{1,10}$";
    public static final String PREVIOUS_POLICY_NUMBER_PATTERN = "^[\\w-./\\s]{1,255}$";
    public static final int HOME_EFFECTIVE_DATE_MIN = 1;
    public static final int HOME_EFFECTIVE_DATE_MAX = 450;
    public static final int HOME_APARTMENTS_MIN = 1;
    public static final int HOME_APARTMENTS_MAX = 15;
    public static final int HOME_ROOMS_MIN = 1;
    public static final int HOME_ROOMS_MAX = 30;
    public static final int HOME_UNDER_CONSTRUCTION = 2199;
    public static final int HOME_CONSTRUCTED_MORE_THAN_SIX_YEARS_AGO = 1900;
    public static final int HOME_CONSTRUCTION_YEAR_MIN = 6;
    public static final String PRODUCTION_YEAR = "^[0-9]{4}";
    public static final BigDecimal DEFAULT_USER_CATALOGUE_VALUE = new BigDecimal("5000");
    public static final int OLD_CAR_AGE_MAX = 10;
    private static final String VEHICLE_DATA_ALLOWED_CHARS = "\\w\\s\\[\\].,_/\\\\!@#$%^&*()\\-+'=\\?\"âïôòóëéèËÉÈ°²´:";
    public static final String VEHICLE_DATA_PATTERN = "^[" + VEHICLE_DATA_ALLOWED_CHARS + "]+$";
    public static final Pattern VEHICLE_DATA_REPLACEMENT_PATTERN = Pattern.compile("[^" + VEHICLE_DATA_ALLOWED_CHARS + "]");

    private Validator() {
    }

    /**
     * This method validates if the year when the driving licence was issued is max current year, and min the year of birth date plus 17 years
     * Example: Person born in 1980 cannot have a driving license since 1990, because that means the person was 10 years old, and the minimum age to get it is 17
     */
    public static boolean isBeLicenseIssueYearValid(int drivingLicenseIssueYear, @NotNull LocalDate driverDateOfBirth) {
        try {
            return Range.closed(driverDateOfBirth.getYear() + DRIVING_LICENSE_MIN_AGE, Year.now().getValue()).contains(drivingLicenseIssueYear);
        } catch (IllegalArgumentException e) {
            log.trace("Driving license issue year and driver date of birth combination is invalid", e);
            return false;
        }
    }

    public static boolean isBeDateOfBirthValid(@NotNull LocalDate driverDateOfBirth) {
        try {
            int currentYear = Year.now().getValue();
            return Range.closed(currentYear - DATE_OF_BIRTH_MIN_YEAR, currentYear).contains(driverDateOfBirth.getYear());
        } catch (IllegalArgumentException e) {
            log.trace("Date of birth is invalid", e);
            return false;
        }
    }

    public static void checkSuppressedFields(BindingResult bindingResult) {
        if (bindingResult != null && (bindingResult.hasErrors() || bindingResult.getSuppressedFields().length > 0)) {
            log.warn("Request validation has failed as it contains suppressed fields");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public static boolean isValidPartyId(String partyId) {
        if (partyId != null) {
            try {
                UUID.fromString(partyId);
                return true;
            } catch (IllegalArgumentException exception) {
                log.trace("Invalid partyId format", exception);
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isValidConstructionYear(Integer constructionYear) {
        if (constructionYear == null || constructionYear == HOME_UNDER_CONSTRUCTION || constructionYear == HOME_CONSTRUCTED_MORE_THAN_SIX_YEARS_AGO) {
            return true;
        } else {
            final int year = Year.now().getValue();
            return Range.closed(year - HOME_CONSTRUCTION_YEAR_MIN, year).contains(constructionYear);
        }
    }

    public static boolean isVehicleConstructionYearValid(Integer constructionYear) {
        if (constructionYear == null) {
            return true;
        }
        return Range.closed(Year.now().minusYears(CAR_CONSTRUCTION_YEAR_MIN).getValue(), Year.now().getValue()).contains(constructionYear);
    }

}
