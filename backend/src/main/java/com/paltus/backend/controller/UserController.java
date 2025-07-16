package com.paltus.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.model.dto.UserGameStatsDto;
import com.paltus.backend.service.AchievementService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {
    private AchievementService achievementService;

    public UserController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @Operation(description = "Get current user's achievement progress, streak, experience, level and title")
    @GetMapping("/achievements")
    public ResponseEntity<UserGameStatsDto> getUserGameStats() {
        return ResponseEntity.ok(achievementService.getUserGameStats());
    }
}
