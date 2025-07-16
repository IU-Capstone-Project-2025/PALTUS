package com.paltus.backend.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paltus.backend.model.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationCode(String verificationCode);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.streak = u.streak + 1, u.lastActivityTime = :lastActivityTime WHERE u.id = :userId")
    void incrementStreak(@Param("userId") long userId, @Param("lastActivityTime") LocalDate lastActivityTime);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.streak = 1 WHERE u.id = :userId")
    void resetStreak(@Param("userId") long userId);

}
