package com.test.agro.exception;

public class ResourceAccessNotAllowedException extends RuntimeException {
    public ResourceAccessNotAllowedException() {
        super();
    }

    public ResourceAccessNotAllowedException(String message) {
        super(message);
    }
}
