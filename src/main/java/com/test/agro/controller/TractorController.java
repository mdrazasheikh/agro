package com.test.agro.controller;

import com.test.agro.contracts.AuthInterface;
import com.test.agro.contracts.TractorInterface;
import com.test.agro.dto.FieldDto;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.dto.TractorDto;
import com.test.agro.entity.FieldEntity;
import com.test.agro.entity.TractorEntity;
import com.test.agro.exception.AuthException;
import com.test.agro.exception.ResourceAccessNotAllowedException;
import com.test.agro.hawks.ResponseHawk;
import com.test.agro.service.AuthService;
import com.test.agro.service.TractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tractor")
public class TractorController {
    AuthInterface authInterface;
    TractorInterface tractorInterface;

    @Autowired
    public TractorController(AuthService authService, TractorService tractorService) {
        this.authInterface = authService;
        this.tractorInterface = tractorService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody TractorDto tractorDto) {
        TokenClaimDto claimDto;
        try {
            claimDto = authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        TractorEntity tractorEntity;
        try {
            tractorEntity = tractorInterface.create(tractorDto, claimDto);
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendCreated(tractorEntity);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAll() {
        try {
            authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        List<TractorEntity> tractorEntities;
        try {
            tractorEntities = tractorInterface.getAll();
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendOk(tractorEntities);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<?> get(@PathVariable String id) {
        try {
            authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        TractorEntity tractorEntity;
        try {
            tractorEntity = tractorInterface.get(id);
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendOk(tractorEntity);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody TractorDto tractorDto) {
        TokenClaimDto claimDto;
        try {
            claimDto = authInterface.validateToken();
        } catch (AuthException e) {
            return ResponseHawk.sendUnauthorized(e.getMessage());
        }
        TractorEntity tractorEntity;
        try {
            tractorEntity = tractorInterface.update(id, tractorDto, claimDto);
        } catch (ResourceAccessNotAllowedException e) {
            return ResponseHawk.sendForbidden(e.getMessage());
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }

        return ResponseHawk.sendCreated(tractorEntity);
    }
}
