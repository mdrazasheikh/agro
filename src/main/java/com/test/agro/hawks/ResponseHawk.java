package com.test.agro.hawks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHawk {

    public static ResponseEntity<?> sendOk(Object response) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<?> sendCreated(Object response) {
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public static ResponseEntity<?> sendUnauthorized(Object response) {
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<?> sendFailed(Object response) {
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> sendUnprocessable(Object response) {
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ResponseEntity<?> sendForbidden(Object response) {
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
