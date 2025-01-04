package com.niw.backend.payload.response;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 8:10 PM
 **/

public record PaymentResponse(
        float toPayEveryMonth,
        float financingFactor,
        int monthlyPayment,
        float vehiclePrice
) {
}
