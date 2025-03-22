package com.security.user.access.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.user.access.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserLoginAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService detailsService;

	private final JwtUtil jwtUtil = new JwtUtil();

	private Claims claims;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("Customer Filter calling...");

		if (request.getRequestURI().contains("/user")) {
			System.out.println("Bypass the process..");
			filterChain.doFilter(request, response); // Continue the filter chain without processing
			return;
		}

		String tokenHeader = request.getHeader("Authorization");

		if (tokenHeader != null && tokenHeader.contains("Bearer ")) {

			System.out.println("Token ************ " + tokenHeader);
			String token = tokenHeader.substring(7);
			claims = jwtUtil.verifyToken(token);
			System.out.println("Claims : " + claims);
			String username = claims.getSubject();

			UserDetails userDetails = detailsService.loadUserByUsername(username);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());

			// Set the authentication in the SecurityContext
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}

		filterChain.doFilter(request, response);

	}

}
