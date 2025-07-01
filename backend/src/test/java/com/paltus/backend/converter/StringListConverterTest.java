package com.paltus.backend.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringListConverterTest {
    private final StringListConverter converter = new StringListConverter();

    @Test
    @DisplayName("null -> empty string")
    void convertToDatabaseColumn_NullInput_ReturnsEmptyString() {
        assertEquals("", converter.convertToDatabaseColumn(null));
    }

    @Test
    @DisplayName("empty -> empty string")
    void convertToDatabaseColumn_EmptyList_ReturnsEmptyString() {
        assertEquals("", converter.convertToDatabaseColumn(Collections.emptyList()));
    }

    @Test
    @DisplayName("1 element -> string")
    void convertToDatabaseColumn_SingleItem_ReturnsCorrectString() {
        List<String> input = List.of("Single Item");
        assertEquals("Single Item", converter.convertToDatabaseColumn(input));
    }

    @Test
    @DisplayName("Many elements -> unite string")
    void convertToDatabaseColumn_MultipleItems_ReturnsJoinedString() {
        List<String> input = Arrays.asList("First", "Second", "Third");
        assertEquals("First|||Second|||Third", converter.convertToDatabaseColumn(input));
    }

    @Test
    @DisplayName("Spaces are trimmed")
    void convertToDatabaseColumn_TrimsWhitespace() {
        List<String> input = Arrays.asList("  First  ", "  Second ", "   Third   ");
        assertEquals("First|||Second|||Third", converter.convertToDatabaseColumn(input));
    }

    @Test
    @DisplayName("null -> empty list")
    void convertToEntityAttribute_NullInput_ReturnsEmptyList() {
        assertTrue(converter.convertToEntityAttribute(null).isEmpty());
    }

    @Test
    @DisplayName("empty string -> empty list")
    void convertToEntityAttribute_EmptyString_ReturnsEmptyList() {
        assertTrue(converter.convertToEntityAttribute("").isEmpty());
    }

    @Test
    @DisplayName("1 element -> list")
    void convertToEntityAttribute_SingleItem_ReturnsSingleElementList() {
        List<String> result = converter.convertToEntityAttribute("Single Item");
        assertEquals(1, result.size());
        assertEquals("Single Item", result.get(0));
    }

    @Test
    @DisplayName("Many elements -> list")
    void convertToEntityAttribute_MultipleItems_ReturnsCorrectList() {
        List<String> result = converter.convertToEntityAttribute("First|||Second|||Third");
        assertEquals(Arrays.asList("First", "Second", "Third"), result);
    }

}