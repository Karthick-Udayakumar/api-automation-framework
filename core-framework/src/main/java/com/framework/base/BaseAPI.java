package com.framework.base;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class BaseAPI {

    protected Response get(RequestSpecification spec, String endpoint) {
        return given().spec(spec).when().get(endpoint).then().extract().response();
    }

    protected Response post(RequestSpecification spec, String endpoint, Object body) {
        return given().spec(spec).body(body).when().post(endpoint).then().extract().response();
    }

    protected Response put(RequestSpecification spec, String endpoint, Object body) {
        return given().spec(spec).body(body).when().put(endpoint).then().extract().response();
    }

    protected Response patch(RequestSpecification spec, String endpoint, Object body) {
        return given().spec(spec).body(body).when().patch(endpoint).then().extract().response();
    }

    protected Response delete(RequestSpecification spec, String endpoint) {
        return given().spec(spec).when().delete(endpoint).then().extract().response();
    }
}
