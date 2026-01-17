package services;

import com.framework.base.BaseAPI;
import com.framework.base.SpecBuilder;
import com.framework.utils.JsonUtils;
import constants.Endpoints;
import models.Auth;
import models.TokenResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthService extends BaseAPI {

    @Step("Create authentication token")
    public Response createToken(String username, String password) {
        Auth auth = new Auth(username, password);
        return post(SpecBuilder.getBaseSpec(), Endpoints.AUTH, auth);
    }

    @Step("Get token string")
    public String getToken(String username, String password) {
        Response response = createToken(username, password);
        TokenResponse tokenResp = JsonUtils.deserialize(response, TokenResponse.class);
        return tokenResp.getToken();
    }
}
