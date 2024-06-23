package com.dekankilic.satisfying.controller;

import com.dekankilic.satisfying.dto.response.AuthResponse;
import com.dekankilic.satisfying.dto.response.LoginResponse;
import com.dekankilic.satisfying.dto.request.RegisterRequest;
import com.dekankilic.satisfying.dto.request.LoginRequest;
import com.dekankilic.satisfying.service.CartService;
import com.dekankilic.satisfying.service.JwtService;
import com.dekankilic.satisfying.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, CartService cartService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.cartService = cartService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if(authentication.isAuthenticated()){
            String jwt = jwtService.generateToken(request.username());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(LoginResponse.builder().jwt(jwt).username(request.username()).message("Successfully login").build());
        }
        log.info("Invalid username " + request.username());
        throw new UsernameNotFoundException("Invalid username {} " + request.username());
    }
}
