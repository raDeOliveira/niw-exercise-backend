package com.niw.backend.payload.response;


import com.niw.backend.dto.PaymentDTO;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 7:54 PM
 **/

public record UserResponse(
        String username,
        String email,
        PaymentDTO paymentDTO
) {
}
