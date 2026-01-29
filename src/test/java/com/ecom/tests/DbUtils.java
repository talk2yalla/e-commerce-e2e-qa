package com.ecom.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbUtils {

    public static Connection getConnection() throws Exception {
        String url = TestConfig.get("db.url");
        String user = TestConfig.get("db.username");
        String pass = TestConfig.get("db.password");
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
