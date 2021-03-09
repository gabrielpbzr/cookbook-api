package com.github.gabrielpbzr.cookbook.utils;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Unit tests for Strings class
 * @author gabriel
 */
public class StringsTest {
    @Test
    public void should_evaluate_empty_string_as_empty() {
        assertTrue(Strings.isNullOrEmpty(""));
    }
    
    @Test
    public void should_evaluate_null_string_as_empty() {
        assertTrue(Strings.isNullOrEmpty(null));
    }
    
    @Test
    public void should_evaluate_a_string_with_blank_space_as_not_empty() {
        assertFalse(Strings.isNullOrEmpty(" "));
    }
    
    @Test
    public void should_evaluate_a_string_with_more_than_zero_characters_as_not_empty() {
        String[] strings = {"a", "bc", "bcde", "Long text with spaces and so"};
        for (String s : strings) {
            assertFalse(Strings.isNullOrEmpty(s));
        }
    }
}
