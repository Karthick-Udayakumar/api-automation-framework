package com.restfulbooker.tests;

import com.framework.config.ConfigManager;
import services.AuthService;
import services.BookingService;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeClass;

@Epic("Restful Booker API")
public class BaseTest {

    protected AuthService authService;
    protected BookingService bookingService;
    protected ConfigManager config;

    @BeforeClass
    public void setup() {
        config = ConfigManager.getInstance();
        authService = new AuthService();
        bookingService = new BookingService();

        System.out.println("===========================================");
        System.out.println("Environment: " + config.getEnv());
        System.out.println("Base URI: " + config.getBaseURI());
        System.out.println("===========================================");
    }
}
