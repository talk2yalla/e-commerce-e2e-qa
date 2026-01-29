package com.ecom.tests;

import com.ecom.base.BaseTest;

import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) throw new RuntimeException("config.properties not found in src/test/resources");
            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
