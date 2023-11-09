package com.example.calculator.services;

import com.example.calculator.constants.CalculatorConstants;
import com.example.calculator.response.CalculatorResponseHandler;
import com.example.calculator.vo.CalculatorRequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CalculatorOperatorService {

    /**
     * Validates operation request and performs operation
     * @param calculatorRequestBody
     * @return
     */
    public CalculatorResponseHandler performCalculation(CalculatorRequestBody calculatorRequestBody){
        CalculatorResponseHandler calculatorResponse = CalculatorResponseHandler.builder().build();
        boolean hasValidationError = validateRequest(calculatorRequestBody, calculatorResponse);
        if (hasValidationError) {
            return calculatorResponse;
        }
        performCalculation(calculatorRequestBody, calculatorResponse);
        return calculatorResponse;
    }

    /**
     * Performing calculation based on provided operation on given numbers
     * @param calculatorRequestBody
     * @param calculatorResponseHandler
     */
    private void performCalculation(CalculatorRequestBody calculatorRequestBody,
                                    CalculatorResponseHandler calculatorResponseHandler) {
        Double calculatedResult = null;
        try {
            switch (calculatorRequestBody.getOperationToPerform()) {
                case CalculatorConstants.ADDITION:
                    calculatedResult = (double) (calculatorRequestBody.getFirstNumber() + calculatorRequestBody.getSecondNumber());
                    break;
                case CalculatorConstants.SUBTRACTION:
                    calculatedResult = (double) (calculatorRequestBody.getFirstNumber() - calculatorRequestBody.getSecondNumber());
                    break;
                case CalculatorConstants.MULTIPLICATION:
                    calculatedResult = (double) (calculatorRequestBody.getFirstNumber() * calculatorRequestBody.getSecondNumber());
                    break;
                case CalculatorConstants.DIVISION:
                    calculatedResult = (double) (calculatorRequestBody.getFirstNumber() / calculatorRequestBody.getSecondNumber());
                    break;
                default:
                    calculatorResponseHandler.setMessage("Please provide valid operation between " +
                            "Addition(+), Subtraction(-), Multiplication(*), and Division(/)");
                    log.error("Aborting the calculation since no valid operators found");
                    break;
            }
        } catch (Exception exception) {
            calculatorResponseHandler.setMessage("Invalid values provided please check application logs for more details");
            log.error("Exception occurred while performing calculation, Reason: ", exception);
        }
        if (calculatedResult != null) {
            calculatorResponseHandler.setResult(calculatedResult);
            calculatorResponseHandler.setMessage("Successfully performed calculation for digits "
                    + calculatorRequestBody.getFirstNumber() + " and "
                    + calculatorRequestBody.getSecondNumber());
            log.info("Successfully performed operation:- "
                    + calculatorRequestBody.getFirstNumber() + calculatorRequestBody.getOperationToPerform()
                    + calculatorRequestBody.getSecondNumber()
                    + " = " + calculatedResult);

        }
    }

    /**
     * Validated calculator request data
     * @param calculatorRequestBody
     * @param calculatorResponseHandler
     * @return
     */
    private boolean validateRequest(CalculatorRequestBody calculatorRequestBody,
                                 CalculatorResponseHandler calculatorResponseHandler) {
        if (calculatorRequestBody.getFirstNumber() == null
                && calculatorRequestBody.getSecondNumber() == null) {
            calculatorResponseHandler.setMessage("Please provide any digits to perform operation");
            log.error("Aborting the operation since firstNumber and secondNumber is missing");
            return true;
        }
        if (calculatorRequestBody.getFirstNumber() == null
                || calculatorRequestBody.getSecondNumber() == null) {
            calculatorResponseHandler.setMessage("Can not perform Operation with one digit");
            log.error("Aborting the operation since found only one digit for calculation");
            return true;
        }
        if (calculatorRequestBody.getOperationToPerform() == null) {
            calculatorResponseHandler.setMessage("Please provide a specific operation to perform");
            log.error("Aborting the operation since couldn't found any operation specified");
            return true;
        }
        return false;
    }
}
