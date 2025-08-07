package com.security.user.access.controller;

import com.security.user.access.dao.entity.UsersEO;
import com.security.user.access.response.ApiResponseTO;
import com.security.user.access.service.UsersService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseTO<String>> createUser(@Valid @RequestBody UsersEO loginEO) {
        return usersService.createUser(loginEO);
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponseTO<String>> logoutUser(HttpServletRequest httpServletRequest,
                                                            HttpServletResponse httpServletResponse) {
        String jwtToken = httpServletRequest.getHeader("Authorization");
        return usersService.logoutUser(jwtToken.substring(7));
    }

    @GetMapping("/validate")
    public ResponseEntity<Claims> validateToken(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) {
        String jwtToken = httpServletRequest.getHeader("Authorization");
        return usersService.validateToken(jwtToken);
    }

    @GetMapping("/delete")
    public ResponseEntity<ApiResponseTO<String>> deleteUser(HttpServletRequest httpServletRequest,
                                                            HttpServletResponse httpServletResponse,
                                                            @RequestParam @NotBlank String username) {
        String jwtToken = httpServletRequest.getHeader("Authorization");
        return usersService.deleteUser(jwtToken, username);
    }

    @GetMapping("/deactivate")
    public ResponseEntity<ApiResponseTO<String>> deactivateUser(HttpServletRequest httpServletRequest,
                                                                HttpServletResponse httpServletResponse) {
        String jwtToken = httpServletRequest.getHeader("Authorization");
        return usersService.deactivateUser(jwtToken);
    }


    @GetMapping("/activate")
    public ResponseEntity<ApiResponseTO<String>> activateUser(HttpServletRequest httpServletRequest,
                                                              HttpServletResponse httpServletResponse,
                                                              @RequestParam @NotBlank String username) {
        return usersService.activateUser(username);
    }

}
