package com.github.gabrielpbzr.cookbook.utils;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {
    private static final String APP_CONFIG_FILE = "config.properties";
    private static Configuration instance;
    private Properties properties;
    private final Logger logger = LoggerFactory.getLogger(Configuration.class);

    private Configuration() {
        try {
            this.properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(APP_CONFIG_FILE));
        } catch (IOException ioe) {
            logger.error("Configuration file not found.", ioe);
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getValue(String key) {
        return properties.getProperty(key, "");
    }
}
