package com.security.user.access.service;

import org.springframework.http.ResponseEntity;

import com.security.user.access.dao.entity.UsersEO;
import com.security.user.access.response.ApiResponseTO;

public interface UsersService {

	ResponseEntity<ApiResponseTO<String>> loginUser(UsersEO loginEO);

	ResponseEntity<ApiResponseTO<String>> createUser(UsersEO loginEO);

	ResponseEntity<ApiResponseTO<String>> logoutUser(String token);

}
