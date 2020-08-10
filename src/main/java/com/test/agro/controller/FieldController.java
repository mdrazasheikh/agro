package com.test.agro.controller;

import com.test.agro.contracts.AuthInterface;
import com.test.agro.contracts.FieldInterface;
import com.test.agro.dto.FieldDto;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.entity.FieldEntity;
import com.test.agro.exception.AuthException;
import com.test.agro.exception.ResourceAccessNotAllowedException;
import com.test.agro.hawks.ResponseHawk;
import com.test.agro.service.AuthService;
import com.test.agro.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("field")
public class FieldController {

    AuthInterface authInterface;
    FieldInterface fieldInterface;

    @Autowired
    public FieldController(AuthService authService, FieldService fieldService) {
        this.authInterface = authService;
        this.fieldInterface = fieldService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@Valid @RequestBody FieldDto fieldDto) {
        TokenClaimDto claimDto;
        try {
            claimDto = authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        FieldEntity fieldEntity;
        try {
            fieldEntity = fieldInterface.create(fieldDto, claimDto);
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendCreated(fieldEntity);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAll() {
        try {
            authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        List<FieldEntity> fieldEntities;
        try {
            fieldEntities = fieldInterface.getAll();
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendOk(fieldEntities);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<?> get(@PathVariable String id) {
        try {
            authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        FieldEntity fieldEntity;
        try {
            fieldEntity = fieldInterface.get(id);
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendOk(fieldEntity);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody FieldDto fieldDto) {
        TokenClaimDto claimDto;
        try {
            claimDto = authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        FieldEntity fieldEntity;
        try {
            fieldEntity = fieldInterface.update(id, fieldDto, claimDto);
        } catch (ResourceAccessNotAllowedException e) {
            return ResponseHawk.sendForbidden(e.getMessage());
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendOk(fieldEntity);
    }
}
