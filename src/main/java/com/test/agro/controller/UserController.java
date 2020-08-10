package com.test.agro.controller;

import com.test.agro.contracts.UserInterface;
import com.test.agro.dto.UserDto;
import com.test.agro.entity.UserEntity;
import com.test.agro.hawks.ResponseHawk;
import com.test.agro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    UserInterface userInterface;

    @Autowired
    public UserController(UserService userService) {
        this.userInterface = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid UserDto userDto) {
        UserEntity userEntity;
        try {
            userEntity = userInterface.create(userDto);
        } catch (Exception e) {
            return ResponseHawk.sendFailed(e.getMessage());
        }
        
        return ResponseHawk.sendOk(userEntity);
    }
}
