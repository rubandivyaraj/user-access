package com.security.user.access.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.user.access.dao.entity.UsersEO;
import com.security.user.access.response.ApiResponseTO;
import com.security.user.access.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/v1/auth")
public class UserController {

	private UsersService usersService;

	@Autowired
	private void setAuthenticationService(UsersService userLoginService) {
		this.usersService = userLoginService;
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponseTO<String>> loginUser(@RequestBody UsersEO loginEO) {
		return usersService.loginUser(loginEO);
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponseTO<String>> createUser(@RequestBody UsersEO loginEO) {
		return usersService.createUser(loginEO);
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponseTO<String>> logoutUser(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String jwtToken = httpServletRequest.getHeader("Authorization");
		return usersService.logoutUser(jwtToken.substring(7));
	}

	@PostMapping("/validate")
	public ResponseEntity<ApiResponseTO<String>> validateToken(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String jwtToken = httpServletRequest.getHeader("Authorization");
		return usersService.logoutUser(jwtToken.substring(7));
	}

}
