package com.test.agro.service;

import com.test.agro.contracts.UserInterface;
import com.test.agro.dto.UserDto;
import com.test.agro.entity.UserEntity;
import com.test.agro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserInterface {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity create(UserDto userDto) {
        return null;
    }
}
