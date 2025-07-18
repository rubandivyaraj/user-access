package com.security.user.access.security;

import com.security.user.access.exception.UserAccessException;
import com.security.user.access.exception.UserAccessExceptionCode;
import com.security.user.access.util.BlacklistTokensUtil;
import com.security.user.access.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class UserLoginAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil = new JwtUtil();

    private final BlacklistTokensUtil blacklistTokensUtil;

    private final UserDetailsService detailsService;

    public UserLoginAuthenticationFilter(BlacklistTokensUtil blacklistTokensUtil, UserDetailsService detailsService) {
        this.blacklistTokensUtil = blacklistTokensUtil;
        this.detailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Customer Filter calling...");
        if (request.getRequestURI().contains("/user")) {
            log.info("Bypass the process..");
            filterChain.doFilter(request, response); // Continue the filter chain without processing
            return;
        }

        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader == null || !tokenHeader.contains("Bearer ")) {
            throw new UserAccessException(UserAccessExceptionCode.TOKEN_NOT_FOUND);
        }

        String token = tokenHeader.substring(7);
        log.info("Token : {} ", tokenHeader);

        if (blacklistTokensUtil.isTokenBlocked(token)) {
            throw new UserAccessException(UserAccessExceptionCode.TOKEN_INVALID);
        }

        Claims claims = jwtUtil.verifyToken(token);
        log.info("Claims : {}", claims);
        String username = claims.getSubject();

        UserDetails userDetails = detailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        // Set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
