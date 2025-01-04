package com.niw.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @project: backend-imechanic
 * @author: raDeOliveira
 * @date: 1/29/2024
 * @time: 11:04 AM
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CustomEntityNotFoundException extends RuntimeException {

    public CustomEntityNotFoundException(String className, int id) {
        super(String.format("%s not found: %d", className, id));
    }
}
