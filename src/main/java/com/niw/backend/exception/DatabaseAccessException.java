package com.niw.backend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @project: backend-imechanic
 * @author: raDeOliveira
 * @date: 10/04/2024
 * @time: 11:04 AM
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class DatabaseAccessException extends RuntimeException {

    public DatabaseAccessException(Throwable e) {
        super("Error accessing the database", e);
    }


}
