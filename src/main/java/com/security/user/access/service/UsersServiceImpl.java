package com.security.user.access.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.user.access.dao.entity.UsersEO;
import com.security.user.access.dao.repo.UsersRepository;
import com.security.user.access.response.ApiResponseTO;
import com.security.user.access.util.JwtUtil;

@Service
public class UsersServiceImpl implements UsersService {

	private final JwtUtil jwtUtil;

	private final AuthenticationManager authenticationManager;

	private final PasswordEncoder passwordEncoder;

	private final UsersRepository usersRepository;

	public UsersServiceImpl(JwtUtil jwtUtil, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.usersRepository = usersRepository;
	}

	@Override
	public ResponseEntity<ApiResponseTO<String>> createUser(UsersEO loginEO) {
		ApiResponseTO<String> apiResponseTO;
		loginEO.setPassword(passwordEncoder.encode(loginEO.getPassword()));
		loginEO.setRole("USER");
		loginEO.setEnabled(true);
		UsersEO userDetail = usersRepository.saveAndFlush(loginEO);
		apiResponseTO = new ApiResponseTO<>(HttpStatus.CREATED, null, userDetail.getUserName());
		return ResponseEntity.ok(apiResponseTO);
	}

	@Override
	public ResponseEntity<ApiResponseTO<String>> loginUser(UsersEO loginEO) {
		ApiResponseTO<String> apiResponseTO;
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginEO.getUserName(), loginEO.getPassword()));

		if (authentication.isAuthenticated())
			apiResponseTO = new ApiResponseTO<>(HttpStatus.ACCEPTED, null,
					jwtUtil.generateToken(loginEO.getUserName()));
		else
			apiResponseTO = new ApiResponseTO<>(HttpStatus.UNAUTHORIZED, null,
					"Authentication Failed - User not found");
		return ResponseEntity.ok(apiResponseTO);
	}

	@Override
	public ResponseEntity<ApiResponseTO<String>> logoutUser(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
