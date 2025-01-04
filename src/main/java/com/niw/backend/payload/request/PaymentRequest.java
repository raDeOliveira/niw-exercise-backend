package com.niw.backend.payload.request;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 8:10 PM
 **/

public record PaymentRequest(
        float toPayEveryMonth,
        float financingFactor,
        int monthlyPayment,
        float vehiclePrice
) {
}
