package com.framework.base;

import com.framework.config.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class SpecBuilder {

    public static RequestSpecification getBaseSpec() {
        String baseURI = ConfigManager.getInstance().getBaseURI();
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification getSpecWithCookieAuth() {
        String baseURI = ConfigManager.getInstance().getBaseURI();
        String token = ConfigManager.getInstance().getCookieToken();
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .addHeader("Cookie", "token=" + token)
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification getSpecWithBearerAuth() {
        String baseURI = ConfigManager.getInstance().getBaseURI();
        String token = ConfigManager.getInstance().getBearerToken();
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
