package com.niw.backend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class DatabaseAccessException extends RuntimeException {

    public DatabaseAccessException(Throwable e) {
        super("Error accessing the database", e);
    }


}
