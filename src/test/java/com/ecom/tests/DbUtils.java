package com.ecom.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbUtils {

    public static Connection getConnection() throws Exception {

        // 1) CI / environment variables (highest priority)
        String url  = System.getenv("DB_URL");
        String user = System.getenv("DB_USERNAME");
        String pass = System.getenv("DB_PASSWORD");

        // 2) Fallback to config.properties (local default)
        if (url == null || url.isBlank())  url  = TestConfig.get("db.url");
        if (user == null || user.isBlank()) user = TestConfig.get("db.username");
        if (pass == null || pass.isBlank()) pass = TestConfig.get("db.password");

        // 3) Final safety check
        if (url == null || url.isBlank()) {
            throw new IllegalStateException("DB_URL/db.url is not set");
        }
        if (user == null || user.isBlank()) {
            throw new IllegalStateException("DB_USERNAME/db.username is not set");
        }
        if (pass == null) {
            throw new IllegalStateException("DB_PASSWORD/db.password is not set");
        }

        return DriverManager.getConnection(url, user, pass);
    }

    public static ResultSet selectUserByEmail(Connection con, String email) throws Exception {
        PreparedStatement ps = con.prepareStatement(
                "SELECT id, email, password_hash, full_name, phone, role, status, created_at, updated_at " +
                        "FROM users WHERE email = ?"
        );
        ps.setString(1, email);
        return ps.executeQuery();
    }
}
