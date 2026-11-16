package com.example.pizzaportal.config;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Adds a short text to every page model that says which database and Spring profile are in use.
 * It is shown on the Database page, so it is easy to see whether the application runs on
 * H2 (local) or on MySQL (Linux server).
 */
@ControllerAdvice
public class RuntimeInfoAdvice {

    private final String dbInfo;

    public RuntimeInfoAdvice(DataSource dataSource, Environment env) {
        String db;
        try (Connection c = dataSource.getConnection()) {
            String product = c.getMetaData().getDatabaseProductName();
            String url = c.getMetaData().getURL();
            int q = url.indexOf('?');
            if (q > 0) url = url.substring(0, q);
            db = product + " (" + url + ")";
        } catch (Exception e) {
            db = "unknown";
        }
        String[] profiles = env.getActiveProfiles();
        String profile = profiles.length == 0 ? "default" : String.join(", ", profiles);
        this.dbInfo = "Database in use: " + db + " | Spring profile: " + profile;
    }

    @ModelAttribute("dbInfo")
    public String dbInfo() {
        return dbInfo;
    }
}