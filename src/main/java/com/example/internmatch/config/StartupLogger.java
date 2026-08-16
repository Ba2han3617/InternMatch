package com.example.internmatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {

    private static final Logger log = LoggerFactory.getLogger(StartupLogger.class);
    private final Environment environment;

    public StartupLogger(Environment environment) {
        this.environment = environment;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");

        if (contextPath == null || "/".equals(contextPath)) {
            contextPath = "";
        } else if (!contextPath.startsWith("/")) {
            contextPath = "/" + contextPath;
        }

        String baseUrl = "http://localhost:" + port + contextPath;

        String border = "========================================";
        String message = String.format(
                "\n%s\nInternMatch Backend calisiyor\nBackend URL: %s\nHealth Check: %s/api/health\nSwagger UI: %s/swagger-ui.html\nOpenAPI Docs: %s/v3/api-docs\n%s",
                border, baseUrl, baseUrl, baseUrl, baseUrl, border
        );

        log.info(message);
    }
}
