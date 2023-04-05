package com.cg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class BalanceNotEnough extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BalanceNotEnough(String message) {
        super(message);
    }
}
