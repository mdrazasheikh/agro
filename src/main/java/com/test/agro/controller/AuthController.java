package com.test.agro.controller;

import com.test.agro.contracts.AuthInterface;
import com.test.agro.dto.LoginDto;
import com.test.agro.dto.LoginResponseDto;
import com.test.agro.exception.LoginException;
import com.test.agro.hawks.ResponseHawk;
import com.test.agro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthInterface authInterface;

    @Autowired
    public AuthController(AuthService authService) {
        this.authInterface = authService;
    }

    @PostMapping("/")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        LoginResponseDto responseDto;
        try {
            responseDto = authInterface.authenticateUser(loginDto);
        } catch (LoginException e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendOk(responseDto);
    }
}
