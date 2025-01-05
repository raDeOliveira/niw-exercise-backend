package com.niw.backend.exception;

import com.niw.backend.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            {
                    CustomEntityNotFoundException.class,
                    AlreadyExistsException.class,
                    DatabaseAccessException.class,
            }
    )
    @Nullable
    public final ResponseEntity<ErrorDTO> handleExceptions(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        log.error("Handling '{}' due to '{}'", ex.getClass().getSimpleName(), ex.getMessage());

        if (ex instanceof CustomEntityNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleEntityNotFoundException((CustomEntityNotFoundException) ex, headers, status, request);
        } else if (ex instanceof AlreadyExistsException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleAlreadyExistException((AlreadyExistsException) ex, headers, status, request);
        } else if (ex instanceof DatabaseAccessException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleDatabaseAccessException((DatabaseAccessException) ex, headers, status, request);
        } else if (ex instanceof CalculationErrorException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleCalculationErrorException((CalculationErrorException) ex, headers, status, request);
        } else {
            if (log.isWarnEnabled()) {
                log.warn("Unknown exception type: {}", ex.getClass().getName());
            }
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    // entity not found exception
    protected ResponseEntity<ErrorDTO> handleEntityNotFoundException
    (
            CustomEntityNotFoundException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return handleExceptionInternal(
                e,
                new ErrorDTO(e.getMessage(), status.toString(), LocalDateTime.now().toString()),
                headers,
                status,
                request);
    }

    // already exist exception
    protected ResponseEntity<ErrorDTO> handleAlreadyExistException(
            AlreadyExistsException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return handleExceptionInternal(
                e,
                new ErrorDTO(e.getMessage(), status.toString(), LocalDateTime.now().toString()),
                headers,
                status,
                request);
    }

    // database access
    protected ResponseEntity<ErrorDTO> handleDatabaseAccessException(
            DatabaseAccessException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return handleExceptionInternal(
                e,
                new ErrorDTO(e.getMessage(), status.toString(), LocalDateTime.now().toString()),
                headers,
                status,
                request);
    }
    // calculation error
    protected ResponseEntity<ErrorDTO> handleCalculationErrorException(
            CalculationErrorException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return handleExceptionInternal(
                e,
                new ErrorDTO(e.getMessage(), status.toString(), LocalDateTime.now().toString()),
                headers,
                status,
                request);
    }

    // handle exception internal
    protected ResponseEntity<ErrorDTO> handleExceptionInternal
    (
            Exception ex, @Nullable ErrorDTO body,
            HttpHeaders headers, HttpStatus status,
            WebRequest request
    ) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }


}
