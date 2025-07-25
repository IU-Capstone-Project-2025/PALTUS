package com.paltus.backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean enabled;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private List<Course> courses;

    /**
     * Number of consecutive days the user has completed subtopics.
     */
    private int streak;

    /**
     * The date when the user last completed a subtopic.
     */
    private LocalDate lastActivityTime;

    private int currentExp;
    private int requiredExp;
    private int level;

    private int finishedLessons;
    private int finishedQuizzes;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    /**
     * Initialize default values on user creation.
     */
    @PrePersist
    public void onCreate() {
        if (level == 0) {
            level = 1;
        }

        if (requiredExp == 0) {
            requiredExp = 100;
        }

        if (currentExp == 0) {
            currentExp = 0;
        }
    }
}
