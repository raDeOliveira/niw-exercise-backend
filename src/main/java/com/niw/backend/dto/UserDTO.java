package com.niw.backend.dto;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:34 PM
 **/

public record UserDTO(
        String name,
        String email,
        PaymentDTO paymentDTO
) {
}
