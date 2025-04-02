package com.security.user.access.service;

import com.security.user.access.dao.entity.UsersEO;
import com.security.user.access.response.ApiResponseTO;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;

public interface UsersService {

    ResponseEntity<ApiResponseTO<String>> loginUser(UsersEO loginEO);

    ResponseEntity<ApiResponseTO<String>> createUser(UsersEO loginEO);

    ResponseEntity<ApiResponseTO<String>> logoutUser(String token);

    ResponseEntity<Claims> validateToken(String token);

}
