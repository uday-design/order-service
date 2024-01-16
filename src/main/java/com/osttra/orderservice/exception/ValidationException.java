package com.osttra.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Validation fasiled")
public class ValidationException extends Throwable {
    public ValidationException(String message) {
        super(message);
    }
}
