package com.niw.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CalculationErrorException extends RuntimeException {

    public CalculationErrorException(String error) {
        super("Error on calculation: " + error);
    }

}
