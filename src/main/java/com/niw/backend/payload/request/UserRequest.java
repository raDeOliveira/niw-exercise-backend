package com.niw.backend.payload.request;


import com.niw.backend.dto.PaymentDTO;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 7:53 PM
 **/

public record UserRequest(
        String name,
        String email,
        PaymentDTO paymentDTO
) {
}
