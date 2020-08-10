package com.test.agro.exception;

public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException() {
        super();
    }

    public ActionNotAllowedException(String message) {
        super(message);
    }
}
