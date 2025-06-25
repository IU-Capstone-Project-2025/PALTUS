package com.paltus.backend.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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
        System.out.println("convertToDatabaseColumn_NullInput_ReturnsEmptyString passed");
    }

    @Test
    @DisplayName("empty -> empty string")
    void convertToDatabaseColumn_EmptyList_ReturnsEmptyString() {
        assertEquals("", converter.convertToDatabaseColumn(Collections.emptyList()));
        System.out.println("convertToDatabaseColumn_EmptyList_ReturnsEmptyString passed");
    }

    @Test
    @DisplayName("1 element -> string")
    void convertToDatabaseColumn_SingleItem_ReturnsCorrectString() {
        List<String> input = List.of("1");
        assertEquals("1", converter.convertToDatabaseColumn(input));
        System.out.println("convertToDatabaseColumn_SingleItem_ReturnsCorrectString passed");

    }

    @Test
    @DisplayName("Many elements -> unite string")
    void convertToDatabaseColumn_MultipleItems_ReturnsJoinedString() {
        List<String> input = Arrays.asList("1", "2", "3");
        assertEquals("1|||2|||3", converter.convertToDatabaseColumn(input));
        System.out.println("convertToDatabaseColumn_MultipleItems_ReturnsJoinedString passed");
    }

    @Test
    @DisplayName("Spaces")
    void convertToDatabaseColumn_TrimsWhitespace() {
        List<String> input = Arrays.asList("  1  ", " 2 ", "  3  ");
        assertEquals("1|||2|||3", converter.convertToDatabaseColumn(input));
        System.out.println("convertToDatabaseColumn_TrimsWhitespace passed");
    }

    @Test
    @DisplayName("null -> empty list")
    void convertToEntityAttribute_NullInput_ReturnsEmptyList() {
        assertTrue(converter.convertToEntityAttribute(null).isEmpty());
        System.out.println("convertToEntityAttribute_NullInput_ReturnsEmptyList passed");
    }

    @Test
    @DisplayName("empty string -> empty list")
    void convertToEntityAttribute_EmptyString_ReturnsEmptyList() {
        assertTrue(converter.convertToEntityAttribute("").isEmpty());
        System.out.println("convertToEntityAttribute_EmptyString_ReturnsEmptyList passed");
    }

    @Test
    @DisplayName("1 element -> list")
    void convertToEntityAttribute_SingleItem_ReturnsSingleElementList() {
        List<String> result = converter.convertToEntityAttribute("1");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0));
        System.out.println("convertToEntityAttribute_SingleItem_ReturnsSingleElementList passed");
    }

    @Test
    @DisplayName("Many elements -> list")
    void convertToEntityAttribute_MultipleItems_ReturnsCorrectList() {
        List<String> result = converter.convertToEntityAttribute("1|||2|||3");
        assertEquals(Arrays.asList("1", "2", "3"), result);
        System.out.println("convertToEntityAttribute_MultipleItems_ReturnsCorrectList passed");
    }
}