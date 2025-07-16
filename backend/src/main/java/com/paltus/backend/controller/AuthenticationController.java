package com.paltus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.model.User;
import com.paltus.backend.model.dto.LoginUserDto;
import com.paltus.backend.model.dto.RegisterUserDto;
import com.paltus.backend.model.dto.VerifyUserDto;
import com.paltus.backend.model.responses.LoginResponse;
import com.paltus.backend.service.AuthenticationService;
import com.paltus.backend.service.JWTService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class AuthenticationController {
    private final JWTService jwtService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JWTService jwtService, AuthenticationService userService) {
        this.jwtService = jwtService;
        this.authenticationService = userService;
    }

    @Operation(description = "Register a new user and sends a verification code via email")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserDto registerUserDto) {
        authenticationService.register(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Registration successful. Please check your email for the verification code.");
    }

    @Operation(description = "Authenticate user and returns a JWT token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser.getEmail());
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @Operation(description = "Verifie user account using the verification code")
    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        authenticationService.verifyUser(verifyUserDto);
        return ResponseEntity.ok("Account verified successfully");
    }

    @Operation(description = "Resend the verification code to the specified email")
    @PostMapping("/resend")
    public ResponseEntity<String> resendVerificationCode(@RequestBody String email) {
        authenticationService.resendVerificationCode(email);
        return ResponseEntity.ok("Verification code sent");
    }
}
