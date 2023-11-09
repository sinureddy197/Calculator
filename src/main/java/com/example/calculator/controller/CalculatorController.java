package com.example.calculator.controller;

import com.example.calculator.response.CalculatorResponseHandler;
import com.example.calculator.services.CalculatorOperatorService;
import com.example.calculator.vo.CalculatorRequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/calculator")
@Slf4j
public class CalculatorController {

    private final CalculatorOperatorService calculatorOperatorService;

    public CalculatorController(CalculatorOperatorService calculatorOperatorService) {
        this.calculatorOperatorService = calculatorOperatorService;
    }

    @PostMapping("/calculate")
    public CalculatorResponseHandler calculate(@RequestBody CalculatorRequestBody calculatorRequestBody) {
        CalculatorResponseHandler calculatorResponse = CalculatorResponseHandler.builder().build();
        if (calculatorRequestBody == null) {
            log.error("Aborting operation since Empty request for Calculation operation");
            calculatorResponse.setMessage("Please provide any content to perform calculation");
            calculatorResponse.setStatus(String.valueOf(HttpStatus.NO_CONTENT));
            return calculatorResponse;
        }
         calculatorResponse =
                calculatorOperatorService.performCalculation(calculatorRequestBody);
        if (calculatorResponse.getResult() == null) {
            calculatorResponse.setStatus(String.valueOf(HttpStatus.BAD_REQUEST));
            return calculatorResponse;
        }
        return calculatorResponse;
    }
}
