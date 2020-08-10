package com.test.agro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenClaimDto {
    String userId;
    String username;
    String role;
    String roleId;
}
