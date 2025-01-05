package com.niw.backend.dto;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

public record PaymentDTO(
        String financingFactor,
        int monthlyPayment,
        float vehiclePrice,
        float toPayEveryMonth
) {
}
