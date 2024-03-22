import helpers.PropertiesReader;
import helpers.PropertiesWriter;
import helpers.TestConfig;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.ErrorModel;
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
                .url(PropertiesReader.getProperty("baseURL")+"v1/user/login/usernamepassword")
                .post(requestBody)
                .build();
        Response response=TestConfig.client.newCall(request).execute();

        if(response.isSuccessful()) {
            AuthResponseModel responseModel =TestConfig.gson.fromJson(response.body().string(), AuthResponseModel.class);
            System.out.println("Response code is: " + response.code());
            System.out.println(responseModel.getToken());
            PropertiesWriter.writeProperties("existingToken", responseModel.getToken(),false);
            Assert.assertTrue(response.isSuccessful());
        }else {
            System.out.println("Error: " + response.code());
            ErrorModel errorModel = TestConfig.gson.fromJson(response.body().string(), ErrorModel.class);
            System.out.println(errorModel.getStatus() + " " + errorModel.getError()+" "+errorModel.getMessage());
            Assert.assertFalse(response.isSuccessful());
        }
    }
}
