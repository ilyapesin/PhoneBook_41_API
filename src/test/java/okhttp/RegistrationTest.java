package okhttp;

import helpers.*;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.ErrorModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTest {
@Test
    public void registrationTest() throws Exception {
    AuthRequestModel requestModel = AuthRequestModel
            .username(EmailGenerator.generateEmail(10,5,3))
            .password(PasswordStringGenerator.generateString());
    PropertiesWriter.writeProperties("existingUserEmail", requestModel.getUsername(),false);
    PropertiesWriter.writeProperties("existingUserPassword", requestModel.getPassword(),false);

    RequestBody requestBody = RequestBody.create(TestConfig.gson.toJson(requestModel), TestConfig.JSON);
    Request request = new Request.Builder()
            .url(PropertiesReader.getProperty("baseURL")+"v1/user/registration/usernamepassword")
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
