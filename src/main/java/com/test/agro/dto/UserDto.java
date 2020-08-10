package com.test.agro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotEmpty(message = "username cannot be empty")
    String username;
    @NotEmpty(message = "password cannot be empty")
    String password;
    @NotEmpty(message = "role cannot be empty")
    String role;
    @NotEmpty(message = "role_id cannot be empty")
    @JsonProperty("role_id")
    String roleId;
}
