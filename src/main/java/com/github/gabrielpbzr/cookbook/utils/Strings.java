package com.github.gabrielpbzr.cookbook.utils;

/**
 * String utilities
 * @author gabriel
 */
public final class Strings {
    private Strings() {}
    
    public static boolean isNullOrEmpty(String str) {
        return (str == null) || str.isEmpty();
    }
}
