package com.security.user.access.service;

import com.security.user.access.dao.entity.UserLoginEO;
import com.security.user.access.dao.repo.UserLoginRepository;
import com.security.user.access.response.ApiResponseTO;
import com.security.user.access.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserLoginRepository userLoginRepository;

    public UserLoginServiceImpl(JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserLoginRepository userLoginRepository) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public ResponseEntity<ApiResponseTO<String>> createUser(UserLoginEO loginEO) {
        ApiResponseTO<String> apiResponseTO;
        loginEO.setPassword(passwordEncoder.encode(loginEO.getPassword()));
        UserLoginEO userDetail = userLoginRepository.saveAndFlush(loginEO);
        apiResponseTO = new ApiResponseTO<>(HttpStatus.CREATED, "Success", userDetail.getUserName());
        return ResponseEntity.ok(apiResponseTO);
    }

    @Override
    public ResponseEntity<ApiResponseTO<String>> loginUser(UserLoginEO loginEO) {
        ApiResponseTO<String> apiResponseTO;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginEO.getUserName(), loginEO.getPassword()));

        if (authentication.isAuthenticated())
            apiResponseTO = new ApiResponseTO<>(HttpStatus.CREATED, "Success", jwtUtil.generateToken(loginEO.getUserName()));
        else
            apiResponseTO = new ApiResponseTO<>(HttpStatus.UNAUTHORIZED, "Failed", "Authentication Failed - User not found");
        return ResponseEntity.ok(apiResponseTO);
    }

    @Override
    public ResponseEntity<ApiResponseTO<String>> logoutUser(String token) {
        // TODO Auto-generated method stub
        return null;
    }

}
