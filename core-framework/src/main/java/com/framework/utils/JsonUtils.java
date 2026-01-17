package com.framework.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;

public class JsonUtils {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T deserialize(Response response, Class<T> clazz) {
        return gson.fromJson(response.asString(), clazz);
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String serialize(Object object) {
        return gson.toJson(object);
    }

    public static String prettyPrint(Object object) {
        return gson.toJson(object);
    }
}
