package com.nsoft.api.security.test_support;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class TestResources {

    static {
        THE_EVERLASTING_TOKEN = readResource("token.txt");
        THE_ALMIGHTY_KEY = readResource("jwks.json");
    }

    public static final String THE_EVERLASTING_TOKEN;
    public static final String THE_ALMIGHTY_KEY;

    private TestResources() {
    }

    private static String readResource(String path) {
        try {
            return new String(Files.readAllBytes(
                    Paths.get(TestResources.class.getClassLoader().getResource(path).toURI())));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
