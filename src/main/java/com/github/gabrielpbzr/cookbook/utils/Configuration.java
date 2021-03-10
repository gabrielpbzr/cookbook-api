package com.github.gabrielpbzr.cookbook.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {

    private static Configuration instance;
    private Map<String, String> properties;
    private Logger logger = LoggerFactory.getLogger(Configuration.class);

    private Configuration() {
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
            instance.load();
        }
        return instance;
    }

    public String getValue(String key) {
        return properties.getOrDefault(key, "");
    }

    /**
     * Load configuration from .env files
     */
    private void load() {
        try {
            this.properties = new HashMap<>(System.getenv());
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(".env")));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                int pos = line.indexOf("=");
                if (pos > -1) {
                    String key = line.substring(0, pos).trim();
                    String value = line.substring(pos + 1).trim();
                    properties.put(key, value);
                }
            }
        } catch (IOException ioe) {
            logger.error("Error loading environment variables");
        }
    }
}
