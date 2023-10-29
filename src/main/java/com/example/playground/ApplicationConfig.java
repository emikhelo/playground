package com.example.playground;

import java.time.LocalDate;

/*
 * Application config is intended to allow mocking today's date for testing.
 * For more elaborate implementation Spring framework can be used.
 */
public class ApplicationConfig {
    private static ApplicationConfig instance;
    protected ApplicationConfig() {};
    public static ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }

    public static void setInstance(ApplicationConfig testConfig) {
        instance = testConfig;
    }

    public LocalDate getToday() {
        // We assume the application server is using the same timezone as playground location
        return LocalDate.now();
    }
}
