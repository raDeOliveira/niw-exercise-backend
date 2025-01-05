package com.niw.backend.dto;

import lombok.Builder;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

@Builder
public record ErrorDTO(
        String message,
        String response,
        String timestamp
) {
}