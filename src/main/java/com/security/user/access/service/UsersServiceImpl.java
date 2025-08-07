package com.security.user.access.service;

import com.security.user.access.dao.entity.UsersEO;
import com.security.user.access.dao.repo.UsersRepository;
import com.security.user.access.exception.UserAccessException;
import com.security.user.access.exception.UserAccessExceptionCode;
import com.security.user.access.response.ApiResponseTO;
import com.security.user.access.util.BlacklistTokensUtil;
import com.security.user.access.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersServiceImpl implements UsersService {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    private final BlacklistTokensUtil blacklistTokensUtil;

    public UsersServiceImpl(JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                            PasswordEncoder passwordEncoder, UsersRepository usersRepository, BlacklistTokensUtil blacklistTokensUtil) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.blacklistTokensUtil = blacklistTokensUtil;
    }

    @Override
    public ResponseEntity<ApiResponseTO<String>> createUser(UsersEO loginEO) {

        UsersEO findUser = usersRepository.findByUsernameAndIsEnabled(loginEO.getUsername(), true);

        // Here @ControllerAdvice will handle this exception.
        if (findUser != null)
            throw new UserAccessException(UserAccessExceptionCode.USER_EXIST);

        ApiResponseTO<String> apiResponseTO;

        loginEO.setPassword(passwordEncoder.encode(loginEO.getPassword()));
        loginEO.setRole("USER");
        loginEO.setEnabled(true);

        UsersEO userDetail = usersRepository.saveAndFlush(loginEO);
        apiResponseTO = new ApiResponseTO<>(HttpStatus.CREATED, null, userDetail.getUsername());
        return ResponseEntity.ok(apiResponseTO);
    }

    @Override
    public ResponseEntity<ApiResponseTO<String>> loginUser(UsersEO loginEO) {
        ApiResponseTO<String> apiResponseTO;
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginEO.getUsername(), loginEO.getPassword()));

        if (authentication.isAuthenticated())
            apiResponseTO = new ApiResponseTO<>(HttpStatus.ACCEPTED, null,
                    jwtUtil.generateToken(loginEO.getUsername()));
        else
            apiResponseTO = new ApiResponseTO<>(HttpStatus.UNAUTHORIZED, UserAccessExceptionCode.USER_INVALID.getMessage(),
                    null);
        return ResponseEntity.ok(apiResponseTO);
    }

    @Override
    public ResponseEntity<ApiResponseTO<String>> logoutUser(String token) {
        setBlacklistTokensUtil(token);
        return ResponseEntity.ok(new ApiResponseTO<>(HttpStatus.OK, "Successfully logged out...", null));
    }

    @Override
    public ResponseEntity<Claims> validateToken(String token) {
        String jwtToken = token.substring(7);
        Claims claims = jwtUtil.verifyToken(jwtToken);
        if (claims.getExpiration().after(new Date()))
            return ResponseEntity.ok(claims);
        else
            throw new UserAccessException(UserAccessExceptionCode.TOKEN_INVALID);
    }

    @Override
    public ResponseEntity<ApiResponseTO<String>> deactivateUser(String token) {
        String jwtToken = token.substring(7);
        setBlacklistTokensUtil(jwtToken);
        String username = jwtUtil.verifyToken(jwtToken).getSubject();
        UsersEO usersEO = this.usersRepository.findByUsername(username);

        if (usersEO == null)
            throw new UserAccessException(UserAccessExceptionCode.USER_INVALID);

        if (!usersEO.isEnabled())
            throw new UserAccessException(UserAccessExceptionCode.USER_DEACTIVATED);

        usersEO.setEnabled(false);
        this.usersRepository.saveAndFlush(usersEO);

        return ResponseEntity.ok(new ApiResponseTO<>(HttpStatus.OK, "Successfully Deactivated...", null));
    }

    @Override
    public ResponseEntity<ApiResponseTO<String>> activateUser(String username) {

        UsersEO usersEO = this.usersRepository.findByUsername(username);

        if (usersEO == null)
            throw new UserAccessException(UserAccessExceptionCode.USER_INVALID);

        usersEO.setEnabled(true);
        this.usersRepository.saveAndFlush(usersEO);

        return ResponseEntity.ok(new ApiResponseTO<>(HttpStatus.OK, "User activated sucessfully...", null));

    }

    @Override
    public ResponseEntity<ApiResponseTO<String>> deleteUser(String token, String username) {
        String jwtToken = token.substring(7);
        UsersEO usersEO = this.usersRepository.findByUsername(username);

        if (usersEO == null)
            throw new UserAccessException(UserAccessExceptionCode.USER_INVALID);

        this.usersRepository.deleteById(usersEO.getUserId());
        return ResponseEntity.ok(new ApiResponseTO<>(HttpStatus.OK, "User deleted permanently...", null));
    }

    private void setBlacklistTokensUtil(String token) {
        Long key = jwtUtil.verifyToken(token).getExpiration().getTime();
        blacklistTokensUtil.addToken(key, token);
    }

}
