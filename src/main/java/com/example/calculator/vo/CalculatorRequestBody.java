package com.example.calculator.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CalculatorRequestBody {
    private Integer firstNumber;
    private Integer secondNumber;
    private String operationToPerform;
}
