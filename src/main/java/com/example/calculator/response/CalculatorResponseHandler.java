package com.example.calculator.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CalculatorResponseHandler {
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double result;
    private String message;
}
