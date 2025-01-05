package com.niw.backend.payload.request;

import com.niw.backend.dto.PaymentDTO;
import lombok.Builder;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 7:53 PM
 **/

@Builder
public record UserRequest(
        String name,
        String email,
        PaymentDTO paymentDTO
) {
}
