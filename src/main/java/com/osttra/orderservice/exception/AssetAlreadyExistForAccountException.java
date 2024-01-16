package com.osttra.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Asset already exist for account")
public class AssetAlreadyExistForAccountException extends Exception {
    public AssetAlreadyExistForAccountException(String message) {
        super(message);
    }
}
