package com.security.user.access.controller;

import com.security.user.access.dao.entity.UserLoginEO;
import com.security.user.access.response.ApiResponseTO;
import com.security.user.access.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserLoginService userLoginService;

	@Autowired
	private void setAuthenticationService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponseTO<String>> loginUser(@RequestBody UserLoginEO loginEO) {
		return userLoginService.loginUser(loginEO);
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponseTO<String>> createUser(@RequestBody UserLoginEO loginEO) {
		return userLoginService.createUser(loginEO);
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponseTO<String>> logoutUser(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String jwtToken = httpServletRequest.getHeader("Authorization");
		return userLoginService.logoutUser(jwtToken.substring(7));
	}
}
