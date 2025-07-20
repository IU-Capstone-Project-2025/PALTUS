package com.paltus.backend.converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA converter that transforms a list of strings to a single string (for database storage)
 * and back. Uses "|||" as a separator.
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = "\\|\\|\\|";
    private static final String JOIN_CHAR = "|||";


    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }

        return attribute.stream()
                .map(String::trim)
                .collect(Collectors.joining(JOIN_CHAR));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.asList(dbData.split(SPLIT_CHAR));

    }
}
