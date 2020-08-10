package com.test.agro.contracts;

import com.test.agro.dto.LoginDto;
import com.test.agro.dto.LoginResponseDto;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.exception.AuthException;
import com.test.agro.exception.LoginException;

public interface AuthInterface {
    LoginResponseDto authenticateUser(LoginDto loginDto) throws LoginException;

    TokenClaimDto validateToken() throws AuthException;
}
