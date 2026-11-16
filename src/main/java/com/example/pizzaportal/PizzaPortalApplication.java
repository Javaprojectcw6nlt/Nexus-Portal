package com.example.pizzaportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PizzaPortalApplication extends SpringBootServletInitializer {

    // Local run (mvnw spring-boot:run or java -jar): uses application.properties -> H2 database.
    public static void main(String[] args) {
        SpringApplication.run(PizzaPortalApplication.class, args);
    }

    // Runs only when the WAR is deployed to Tomcat (rivendell server):
    // activates the "server" profile, so application-server.properties (MySQL) overrides the H2 settings.
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.profiles("server").sources(PizzaPortalApplication.class);
    }
}