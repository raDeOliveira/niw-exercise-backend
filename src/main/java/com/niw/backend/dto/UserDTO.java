package com.niw.backend.dto;

import lombok.Builder;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:34 PM
 **/

@Builder
public record UserDTO(
        String name,
        String email,
        PaymentDTO paymentDTO
) {
}
