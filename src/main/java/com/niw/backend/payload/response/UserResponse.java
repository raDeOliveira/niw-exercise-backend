package com.niw.backend.payload.response;

import com.niw.backend.dto.PaymentDTO;
import lombok.Builder;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 7:54 PM
 **/

@Builder
public record UserResponse(
        String name,
        String email,
        PaymentDTO paymentDTO
) {
}
