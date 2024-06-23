package com.dekankilic.satisfying.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderStatusNotValidException extends RuntimeException{
    public OrderStatusNotValidException(String resourceName) {
        super(String.format("%s not valid ", resourceName));
    }

}
