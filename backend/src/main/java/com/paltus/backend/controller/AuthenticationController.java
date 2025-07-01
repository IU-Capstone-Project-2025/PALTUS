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
import com.paltus.backend.model.dto.ResendDto;
import com.paltus.backend.model.dto.VerifyUserDto;
import com.paltus.backend.model.responses.LoginResponse;
import com.paltus.backend.service.AuthenticationService;
import com.paltus.backend.service.JWTService;

@RestController
public class AuthenticationController {
    private final JWTService jwtService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JWTService jwtService, AuthenticationService userService) {
        this.jwtService = jwtService;
        this.authenticationService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserDto registerUserDto) {
        authenticationService.register(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Registration successful. Please check your email for the verification code.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser.getEmail());
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        authenticationService.verifyUser(verifyUserDto);
        return ResponseEntity.ok("Account verified successfully");
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendVerificationCode(@RequestBody ResendDto resendDto) {
        authenticationService.resendVerificationCode(resendDto.email());
        return ResponseEntity.ok("Verification code sent");
    }
}
