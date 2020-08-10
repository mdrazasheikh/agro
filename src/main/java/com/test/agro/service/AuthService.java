package com.test.agro.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.test.agro.contracts.AuthInterface;
import com.test.agro.dto.LoginDto;
import com.test.agro.dto.LoginResponseDto;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.entity.UserEntity;
import com.test.agro.exception.AuthException;
import com.test.agro.exception.LoginException;
import com.test.agro.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.test.agro.constants.SecurityConstants.*;

@Service
@Slf4j
public class AuthService implements AuthInterface {

    UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponseDto authenticateUser(LoginDto loginDto) throws LoginException {
        log.info("Request : {}", loginDto);
        UserEntity userEntity = getUserByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        log.info("Resp : {}", userEntity);
        String jwt = createJWT(userEntity);

        return new LoginResponseDto(jwt);
    }

    private String createJWT(UserEntity userEntity) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, EXPIRATION_DAYS);

        return JWT.create()
                .withSubject(userEntity.getUsername())
                .withClaim("username", userEntity.getUsername())
                .withClaim("user_id", userEntity.getId())
                .withClaim("role", userEntity.getRole())
                .withClaim("role_id", userEntity.getRoleId())
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .withIssuedAt(now)
                .sign(Algorithm.HMAC256(KEY.getBytes()));
    }

    private UserEntity getUserByUsernameAndPassword(String username, String password) throws LoginException {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.getByUsernameAndPassword(username, password);
        } catch (Exception e) {
            log.error("Failed to fetch data from repository");
            log.error(e.getMessage());
            if (log.isTraceEnabled()) {
                e.printStackTrace();
            }
        }

        if (userEntity == null || userEntity.getId() == null || userEntity.getId().isEmpty()) {
            throw new LoginException("Invalid credentials");
        }

        return userEntity;
    }

    public TokenClaimDto validateToken() throws AuthException {
        String jwt = this.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("JWT : {}", jwt);
        if (jwt == null || jwt.isEmpty()) {
            throw new AuthException("Unauthorized Access");
        }
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(KEY.getBytes()))
                    .withIssuer(ISSUER)
                    .build();
            Map<String, Claim> claimMap = verifier.verify(jwt).getClaims();
            TokenClaimDto claimDto = new TokenClaimDto();
            claimDto.setUserId(claimMap.get("user_id").asString());
            claimDto.setUsername(claimMap.get("username").asString());
            claimDto.setRole(claimMap.get("role").asString());
            claimDto.setRoleId(claimMap.get("role_id").asString());

            return claimDto;
        } catch (JWTVerificationException exception) {
            log.info("Jwt: Invalid signature");
            throw new AuthException("Unauthorized Access. Invalid JWT");
        } catch (Exception e) {
            throw new AuthException("Unauthorized Access. Invalid JWT");
        }
    }

    private String getHeader(String name) {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            return request.getHeader(name);
        }
        return null;
    }
}
