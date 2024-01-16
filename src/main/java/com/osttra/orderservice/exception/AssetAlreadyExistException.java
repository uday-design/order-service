package com.osttra.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Asset already exist")
public class AssetAlreadyExistException extends Exception {
    public AssetAlreadyExistException(String message) {
        super(message);
    }
}
