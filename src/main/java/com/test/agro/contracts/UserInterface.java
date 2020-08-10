package com.test.agro.contracts;

import com.test.agro.dto.UserDto;
import com.test.agro.entity.UserEntity;

public interface UserInterface {
    UserEntity create(UserDto userDto);
}
