package com.github.gabrielpbzr.cookbook;

import com.github.gabrielpbzr.cookbook.utils.Configuration;
import com.github.gabrielpbzr.cookbook.utils.Strings;

public class Main {
    private static final int DEFAULT_PORT = 7000;

    public static void main(String[] args) {
        Application app = new Application(Configuration.getInstance());
        app.start(getPort());
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        if (!Strings.isNullOrEmpty(port)) {
            return Integer.parseInt(port);
        }
        return DEFAULT_PORT;
    }
}
