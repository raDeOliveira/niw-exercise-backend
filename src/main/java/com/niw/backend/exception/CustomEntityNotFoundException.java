package com.niw.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CustomEntityNotFoundException extends RuntimeException {

    public CustomEntityNotFoundException(String className, int id) {
        super(String.format("%s not found: %d", className, id));
    }
}
