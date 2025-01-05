package com.niw.backend.payload.response;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 8:10 PM
 **/

@Builder
public record PaymentResponse(
        float vehiclePrice,
        float financingFactor,
        int monthlyPayment,
        BigDecimal toPayEveryMonth
) {
}
