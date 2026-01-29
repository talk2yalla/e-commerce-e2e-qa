package com.ecom.base;

import com.ecom.tests.TestConfig;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class SuiteSetup {

    @org.testng.annotations.BeforeSuite(alwaysRun = true)
    public void setupSuite() {

        // 1️⃣ Highest priority: CI / environment variable (GitHub Actions)
        String baseUrl = System.getenv("BASE_URL");

        // 2️⃣ Next priority: CLI override (-DbaseUrl=...)
        if (baseUrl == null || baseUrl.isBlank()) {
            baseUrl = System.getProperty("baseUrl");
        }

        // 3️⃣ Fallback: config.properties
        if (baseUrl == null || baseUrl.isBlank()) {
            baseUrl = TestConfig.get("baseUrl");
        }

        // 4️⃣ Final safety check
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException(
                    "baseUrl is not set. Provide BASE_URL env var, -DbaseUrl=..., or set baseUrl in config.properties"
            );
        }

        RestAssured.baseURI = baseUrl;

        // Optional:
        // RestAssured.basePath = "/api/v1";

        System.out.println("✅ RestAssured.baseURI set to: " + RestAssured.baseURI);
    }
}
