package com.security.user.access.security;

import com.security.user.access.util.BlacklistTokensUtil;
import com.security.user.access.util.JwtUtil;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class UserAccessWebSecurity {

    private final JwtUtil jwtUtil;

    public UserAccessWebSecurity(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserLoginAuthenticationFilter userLoginAuthenticationFilter) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(jwtUtil.getPublicUrls())
                        .permitAll().requestMatchers(jwtUtil.getPrivateUrls()).authenticated()).csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(userLoginAuthenticationFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserLoginAuthenticationFilter userLoginAuthenticationFilter(JwtUtil jwtUtil, BlacklistTokensUtil blacklistTokensUtil, UserDetailsService detailsService) {
        return new UserLoginAuthenticationFilter(jwtUtil, blacklistTokensUtil, detailsService);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(9);
    }

}