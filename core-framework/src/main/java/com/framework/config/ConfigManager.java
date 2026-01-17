package com.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static volatile ConfigManager instance;
    private final Properties properties = new Properties();
    private String project;
    private String env;

    private ConfigManager() {
        loadConfig();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private void loadConfig() {
        project = System.getProperty("project", "restfulbooker-tests");
        env = System.getProperty("env", "staging");

        String configPath = project.replace("-tests", "") + "/" + env + ".properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configPath)) {
            if (input == null) {
                throw new RuntimeException("Unable to find config: " + configPath);
            }
            properties.load(input);
            System.out.println("✓ Loaded config: " + configPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config: " + configPath, e);
        }
    }

    public String getProject() {
        return project;
    }

    public String getEnv() {
        return env;
    }

    public String getBaseURI() {
        return properties.getProperty("base.uri");
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

    public String getCookieToken() {
        return properties.getProperty("cookie.token");
    }

    public String getBearerToken() {
        return properties.getProperty("bearer.token");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
