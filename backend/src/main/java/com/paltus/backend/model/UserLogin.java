package com.paltus.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user's login record with the date of login.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private LocalDate loginDate;

    public UserLogin(String email, LocalDate date) {
        this.email = email;
        this.loginDate = date;
    }
}
