package com.security.user.access.service;

import com.security.user.access.dao.entity.UserLoginEO;
import com.security.user.access.response.ApiResponseTO;
import org.springframework.http.ResponseEntity;

public interface UserLoginService {

    ResponseEntity<ApiResponseTO<String>> loginUser(UserLoginEO loginEO);

    ResponseEntity<ApiResponseTO<String>> createUser(UserLoginEO loginEO);

    ResponseEntity<ApiResponseTO<String>> logoutUser(String token);

}
