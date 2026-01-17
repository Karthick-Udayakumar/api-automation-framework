package com.framework.utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class AssertionUtils {

    public static void verifyStatusCode(Response response, int expectedStatusCode) {
        int actual = response.getStatusCode();
        Assert.assertEquals(actual, expectedStatusCode,
                "Status code mismatch! Expected: " + expectedStatusCode + ", Actual: " + actual);
    }

    public static void verifyResponseTime(Response response, long maxTime) {
        long actualTime = response.getTime();
        Assert.assertTrue(actualTime <= maxTime,
                "Response time exceeded! Max: " + maxTime + "ms, Actual: " + actualTime + "ms");
    }

    public static void verifyContentType(Response response, String expectedContentType) {
        String actual = response.getContentType();
        Assert.assertTrue(actual.contains(expectedContentType),
                "Content type mismatch! Expected: " + expectedContentType + ", Actual: " + actual);
    }

    public static void verifyNotNull(Object object, String fieldName) {
        Assert.assertNotNull(object, fieldName + " should not be null");
    }
}
