package com.test.agro.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthException extends RuntimeException {
    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }
}
