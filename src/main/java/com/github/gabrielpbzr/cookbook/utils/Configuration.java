package com.github.gabrielpbzr.cookbook.utils;

public class Configuration {
    private static Configuration instance;

    private Configuration() {        
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getValue(String key) {
        return (System.getenv(key) != null)  ? System.getenv(key) : "";
    }
}
