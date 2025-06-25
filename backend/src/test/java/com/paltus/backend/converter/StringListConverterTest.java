package com.paltus.backend.converter;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringListConverterTest {
    private final StringListConverter converter = new StringListConverter();

    @Test
    void convertToDatabaseColumn_NullInput_ReturnsEmptyString() {
        assertEquals("", converter.convertToDatabaseColumn(null));
    }

    @Test
    void convertToDatabaseColumn_EmptyList_ReturnsEmptyString() {
        assertEquals("", converter.convertToDatabaseColumn(Collections.emptyList()));
    }

    @Test
    void convertToDatabaseColumn_SingleItem_ReturnsCorrectString() {
        List<String> input = List.of("1");
        assertEquals("1", converter.convertToDatabaseColumn(input));
    }

    @Test
    void convertToDatabaseColumn_MultipleItems_ReturnsJoinedString() {
        List<String> input = Arrays.asList("1", "2", "3");
        assertEquals("1|||2|||3", converter.convertToDatabaseColumn(input));
    }

    @Test
    void convertToDatabaseColumn_TrimsWhitespace() {
        List<String> input = Arrays.asList("  1  ", " 2 ", "  3  ");
        assertEquals("1|||2|||3", converter.convertToDatabaseColumn(input));
    }

    @Test
    void convertToEntityAttribute_NullInput_ReturnsEmptyList() {
        assertTrue(converter.convertToEntityAttribute(null).isEmpty());
    }

    @Test
    void convertToEntityAttribute_EmptyString_ReturnsEmptyList() {
        assertTrue(converter.convertToEntityAttribute("").isEmpty());
    }

    @Test
    void convertToEntityAttribute_SingleItem_ReturnsSingleElementList() {
        List<String> result = converter.convertToEntityAttribute("1");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0));
    }

    @Test
    void convertToEntityAttribute_MultipleItems_ReturnsCorrectList() {
        List<String> result = converter.convertToEntityAttribute("1|||2|||3");
        assertEquals(Arrays.asList("1", "2", "3"), result);
    }
}