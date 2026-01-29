package com.ecom.base;

import com.ecom.tests.TestConfig;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class SuiteSetup {

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        String baseUrl = System.getProperty("baseUrl"); // allow override from CLI

        if (baseUrl == null || baseUrl.isBlank()) {
            baseUrl = TestConfig.get("baseUrl"); // fallback to config.properties
        }

        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("baseUrl is not set. Provide -DbaseUrl=... or set baseUrl in config.properties");
        }

        RestAssured.baseURI = baseUrl;

        // Optional: if you keep endpoints as "/api/v1/..."
        // do NOT set basePath. If you want shorter endpoints, then set:
        // RestAssured.basePath = "/api/v1";

        System.out.println("✅ RestAssured.baseURI = " + RestAssured.baseURI);
    }
}
