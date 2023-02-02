package com.example.restkube.controller;

import com.example.restkube.domian.car.CarDetails;
import com.example.restkube.domian.car.CarDetailsList;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static com.example.restkube.util.Validator.checkSuppressedFields;

@RestController
@RequestMapping("/car")
@Slf4j
@RequiredArgsConstructor
public class CarDetailController {

    private static final String[] CAR_DETAILS_ALLOWED_FIELDS = new String[] { "makeCode", "constructionYear", "fuelType", "modelCode", "typeCode" };

    @InitBinder("carDetails")
    public void initCarDetailsBinder(WebDataBinder binder) {
        binder.setAllowedFields(CAR_DETAILS_ALLOWED_FIELDS);
    }

    @ApiOperation(value = "Return the valid Car by Fuel Type/Construction Year/Brand Code/Model Code/Type Code",
            notes = "This method provides a way to lookup the Car by details",
            response = CarDetailsList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK with JSON object containing list of CarDetails details ", response = CarDetails.class),
            @ApiResponse(code = 400, message = "Bad Request - with an explanation of what is wrong with the request ", response = ApiExceptionHandler.class),
            @ApiResponse(code = 404, message = "Not Found - CarDetails not found ", response = ApiExceptionHandler.class),
            @ApiResponse(code = 500, message = "Internal Server Error ", response = ApiExceptionHandler.class)})
    @GetMapping("/details")
    public CompletableFuture<CarDetailsList> findCarByDetails(@ApiParam(hidden = true) @Valid CarDetails carDetails, BindingResult bindingResult) {
        checkSuppressedFields(bindingResult);
        return CompletableFuture.completedFuture(CarDetailsList.builder().build());
    }

}
