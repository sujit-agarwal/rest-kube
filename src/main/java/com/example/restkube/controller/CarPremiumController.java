package com.example.restkube.controller;


import com.example.restkube.domian.car.CarDetails;
import com.example.restkube.domian.car.CarDetailsList;
import com.example.restkube.domian.car.premium.CarPremiumRequest;
import com.example.restkube.domian.car.premium.CarPremiumResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static com.example.restkube.util.Validator.checkSuppressedFields;

@RestController
@RequestMapping("/car")
@Slf4j
@RequiredArgsConstructor
public class CarPremiumController {

    private static final String[] DISALLOWED_FIELDS = new String[]{};


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(DISALLOWED_FIELDS);
    }

    @ApiOperation(value = "Request a premium for car insurance.",
            notes = "Retrieves a premium calculation result.\n" +
                    "Used on the page of the form when all required car details have been provided.\n" +
                    "Response is a collection of premiums per .",
            response = CarPremiumResponse.class,
            responseContainer = "CarPremiumResponse")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - with a JSON object containing car details ", response = CarPremiumResponse.class),
            @ApiResponse(code = 400, message = "Bad Request - with an explanation of what is wrong with the request ", response = ApiExceptionHandler.class),
            @ApiResponse(code = 500, message = "Internal Server Error ", response = ApiExceptionHandler.class)})
    @PostMapping("/premiums")
    public CompletableFuture<CarPremiumResponse> calculatePremium(@ApiParam("carPremiumRequest") @Valid @RequestBody CarPremiumRequest request) {

        log.debug("Request to calculate car premium: {}", request);
        return CompletableFuture.completedFuture(CarPremiumResponse.builder().build());
    }


}
