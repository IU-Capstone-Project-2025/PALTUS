package com.paltus.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user title based on required experience points.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Title {
    @Id
    private int requiredExp;
    private String name;
}
