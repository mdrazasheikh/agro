package com.test.agro.controller;

import com.test.agro.contracts.AuthInterface;
import com.test.agro.contracts.FieldProcessingInterface;
import com.test.agro.dto.FieldProcessingDto;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.entity.FieldProcessingEntity;
import com.test.agro.exception.AuthException;
import com.test.agro.exception.NotFoundException;
import com.test.agro.exception.FieldProcessException;
import com.test.agro.exception.ResourceAccessNotAllowedException;
import com.test.agro.hawks.ResponseHawk;
import com.test.agro.repository.FieldProcessingRepository;
import com.test.agro.service.AuthService;
import com.test.agro.service.FieldProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/field-processing")
public class FieldProcessingController {

    AuthInterface authInterface;
    FieldProcessingInterface fieldProcessingInterface;

    @Autowired
    FieldProcessingRepository fieldProcessingRepository;

    @Autowired
    public FieldProcessingController(FieldProcessingService fieldProcessingService, AuthService authService) {
        this.fieldProcessingInterface = fieldProcessingService;
        this.authInterface = authService;
    }

    @PostMapping("/request")
    public ResponseEntity<?> request(@RequestBody @Valid FieldProcessingDto fieldProcessingDto) {
        TokenClaimDto claimDto;
        try {
            claimDto = authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        FieldProcessingEntity fieldProcessingEntity;
        try {
            fieldProcessingEntity = fieldProcessingInterface.create(fieldProcessingDto, claimDto);
        } catch (NotFoundException | FieldProcessException e) {
            return ResponseHawk.sendFailed(e.getMessage());
        } catch (ResourceAccessNotAllowedException e) {
            return ResponseHawk.sendForbidden(e.getMessage());
        } catch (Exception e) {
            return ResponseHawk.sendUnprocessable(e.getMessage());
        }

        return ResponseHawk.sendOk(fieldProcessingEntity);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAll() {
        try {
            authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        List<FieldProcessingEntity> fieldProcessingEntities;
        try {
            fieldProcessingEntities = fieldProcessingInterface.getAll();
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendOk(fieldProcessingEntities);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<?> get(@PathVariable String id) {
        try {
            authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        FieldProcessingEntity fieldProcessingEntity;
        try {
            fieldProcessingEntity = fieldProcessingInterface.get(id);
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendOk(fieldProcessingEntity);
    }

    @PutMapping("/{id}/process")
    public ResponseEntity<?> markProcessed(@PathVariable String id) {
        TokenClaimDto claimDto;
        try {
            claimDto = authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        if (claimDto.getRole().equals("user")) {
            return ResponseHawk.sendForbidden("cannot perform this action");
        }
        FieldProcessingEntity fieldProcessingEntity;
        try {
            fieldProcessingEntity = fieldProcessingInterface.markIsProcessed(id, claimDto);
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendOk(fieldProcessingEntity);
    }
}
