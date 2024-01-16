package com.osttra.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Asset not found")
public class AssetNotFoundException extends Exception {

    public AssetNotFoundException(String message){
        super(message);
    }
}
