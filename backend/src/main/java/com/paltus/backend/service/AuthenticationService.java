package com.paltus.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paltus.backend.exception.EmailException;
import com.paltus.backend.model.User;
import com.paltus.backend.model.dto.LoginUserDto;
import com.paltus.backend.model.dto.RegisterUserDto;
import com.paltus.backend.model.dto.VerifyUserDto;
import com.paltus.backend.model.enums.AchievementType;
import com.paltus.backend.repository.TitleRepository;
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
    private AchievementService achievementService;
    private TitleRepository titleRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
            EmailService emailService, BCryptPasswordEncoder encoder, AchievementService achievementService,
            TitleRepository titleRepository) {
        this.userRepo = userRepository;
        this.authManager = authenticationManager;
        this.emailService = emailService;
        this.encoder = encoder;
        this.achievementService = achievementService;
        this.titleRepository = titleRepository;

    }

    /**
     * Registers a new user or re-sends verification if a pending account already exists.
     */
    public void register(RegisterUserDto userDto) {
        Optional<User> optionalUser = userRepo.findByEmail(userDto.email());

        // If user exists and is already verified, throw conflict
        if (optionalUser.isPresent() && optionalUser.get().getVerificationCode() == null) {
            throw new EntityExistsException("User already exists");
        }

        User user;
        if (optionalUser.isPresent()) {
            // Reuse existing unverified user
            user = optionalUser.get();
        } else {
            user = new User();
            user.setUsername(userDto.username());
            user.setEmail(userDto.email());
            user.setPassword(encoder.encode(userDto.password()));
            user.setEnabled(false);
            user.setTitle(titleRepository.findById(1).get());
        }

        // Generate and assign a new verification code
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        userRepo.save(user);
        sendVerificationEmail(user);
    }

    /**
     * Authenticates user and updates login streak achievement.
     */
    public User login(LoginUserDto userDto) {
        User user = userRepo.findByEmail(userDto.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified");
        }
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        achievementService.updateProgress(AchievementType.LOGIN_STREAK);
        
        return user;
    }

    /**
     * Verifies a user using a verification code.
     */
    public void verifyUser(VerifyUserDto userDto) {
        Optional<User> optionalUser = userRepo.findByEmail(userDto.email());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code has expired");
            }

            if (user.getVerificationCode().equals(userDto.verificationCode())) {
                // Mark user as verified
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

    /**
     * Resends a new verification code if user is not already verified.
     */
    public void resendVerificationCode(String email) {
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

    /**
     * Sends a styled verification email containing a 6-digit code.
     */
    public void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to PALTUS!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException ex) {
            throw new EmailException("Sending verification email failed");
        }

    }

    /**
     * Generates a random 6-digit numeric verification code.
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

}
