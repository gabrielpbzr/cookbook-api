package com.github.gabrielpbzr.cookbook.utils;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for Configuration class
 * @author gabriel
 */
public class ConfigurationTest {
    private Configuration config = Configuration.getInstance();
    
    @Test
    public void should_load_value_when_provided_existent_key() {
        String value = config.getValue("app.name");
        assertFalse(value.isEmpty());
    }
    
    @Test
    public void should_load_empty_value_when_provided_inexistent_key() {
        String value = config.getValue("appz.namez");
        assertTrue(value.isEmpty());
    }
}
