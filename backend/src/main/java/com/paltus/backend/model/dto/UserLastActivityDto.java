package com.paltus.backend.model.dto;

import java.time.LocalDate;

public record UserLastActivityDto (
    long id,
    LocalDate lastActivityTime
) {}
