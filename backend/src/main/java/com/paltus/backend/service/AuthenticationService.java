package com.paltus.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paltus.backend.model.User;
import com.paltus.backend.model.dto.LoginUserDto;
import com.paltus.backend.model.dto.RegisterUserDto;
import com.paltus.backend.model.dto.VerifyUserDto;
import com.paltus.backend.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthenticationService {
    private UserRepository userRepo;
    private AuthenticationManager authManager;
    private EmailService emailService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
            EmailService emailService, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepository;
        this.authManager = authenticationManager;
        this.emailService = emailService;
        this.encoder = encoder;
    }

    public void register(RegisterUserDto userDto) throws MessagingException {
        if (userRepo.existsByEmail(userDto.email())) {
            throw new EntityExistsException("User already exists");
        }
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(encoder.encode(userDto.password()));
        user.setEnabled(false);
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        userRepo.save(user);
        sendVerificationEmail(user);
    }

    public User login(LoginUserDto userDto) {
        User user = userRepo.findByEmail(userDto.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified");
        }
        authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password()));

        return user;
    }

    public void verifyUser(VerifyUserDto userDto) {
        Optional<User> optionalUser = userRepo.findByEmail(userDto.email());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code has expired");
            }
            if (user.getVerificationCode().equals(userDto.verificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationCodeExpiresAt(null);
                userRepo.save(user);
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public void resendVerificationCode(String email) throws MessagingException {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepo.save(user);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public void sendVerificationEmail(User user) throws MessagingException {
        String subject = "Account Verification";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our PALTUS!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        
        emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);

    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

}
