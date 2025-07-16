package com.paltus.backend.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExpGotEvent {
    private long userId;
    private int exp;
}
