import helpers.PropertiesReader;
import helpers.TestConfig;
import models.AuthRequestModel;
import models.AuthResponseModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {
    @Test
    public void loginPositiveTest() throws IOException {
        AuthRequestModel requestModel = AuthRequestModel
                .username(PropertiesReader.getProperty("existingUserEmail"))
                .password(PropertiesReader.getProperty("existingUserPassword"));

        RequestBody requestBody = RequestBody.create(TestConfig.gson.toJson(requestModel), TestConfig.JSON);
        Request request = new Request.Builder()
                .url(PropertiesReader.getProperty("existingURL"))
                .post(requestBody)
                .build();
        Response response=TestConfig.client.newCall(request).execute();

        if(response.isSuccessful()) {
            AuthResponseModel responseModel =TestConfig.gson.fromJson(response.body().string(), AuthResponseModel.class);
            System.out.println("Response code is: " + response.code());
            System.out.println(responseModel.getToken());
            Assert.assertTrue(response.isSuccessful());
        }else {
            System.out.println("Error: " + response);
        }
    }
}
