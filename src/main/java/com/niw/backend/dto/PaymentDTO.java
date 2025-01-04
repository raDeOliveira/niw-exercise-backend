package com.niw.backend.dto;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

public record PaymentDTO(
        float toPayEveryMonth,
        String financingFactor,
        int monthlyPayment,
        float vehiclePrice
) {
}
