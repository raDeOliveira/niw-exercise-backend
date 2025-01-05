package com.niw.backend.payload.request;

import lombok.Builder;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 8:10 PM
 **/

@Builder
public record PaymentRequest(
        float vehiclePrice,
        String financingFactor,
        int monthlyPayment
) {
}
