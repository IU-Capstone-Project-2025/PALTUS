package com.paltus.backend.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Domain event that is published when a user gains experience points (XP).
 */
@AllArgsConstructor
@Getter
public class ExpGotEvent {
    private long userId;
    private int exp;
}
