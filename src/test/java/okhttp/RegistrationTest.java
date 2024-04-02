package okhttp;

import helpers.*;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.ErrorModel;
import models.NewUserModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class RegistrationTest implements TestConfig{
    int i = new Random().nextInt(1000) + 1000;

    @Test
    public void registrationTest() throws Exception {
        AuthRequestModel requestModel = AuthRequestModel
                .username(EmailGenerator.generateEmail(10, 5, 3))
                .password(PasswordStringGenerator.generateString());
        PropertiesWriter.writeProperties("existingUserEmail", requestModel.getUsername(), false);
        PropertiesWriter.writeProperties("existingUserPassword", requestModel.getPassword(), false);

        RequestBody requestBody = RequestBody.create(gson.toJson(requestModel), JSON);
        Request request = new Request.Builder()
                .url(PropertiesReader.getProperty("baseURL") + "v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();


        if (response.isSuccessful()) {
            AuthResponseModel responseModel = gson.fromJson(response.body().string(), AuthResponseModel.class);
            System.out.println("Response code is: " + response.code());
            System.out.println(responseModel.getToken());
            PropertiesWriter.writeProperties("existingToken", responseModel.getToken(), false);
            Assert.assertTrue(response.isSuccessful());
        } else {
            System.out.println("Error: " + response.code());
            ErrorModel errorModel = gson.fromJson(response.body().string(), ErrorModel.class);
            System.out.println(errorModel.getStatus() + " " + errorModel.getError() + " " + errorModel.getMessage());
            Assert.assertFalse(response.isSuccessful());
        }
    }

    @Test
    public void registrationTestUser() throws Exception {
        NewUserModel newUserModel = NewUserModel.builder()
                .username(EmailGenerator.generateEmail(10, 5, 3))
                .password(PasswordStringGenerator.generateString())
                .build();
        // System.out.println(newUserModel);
        PropertiesWriterXML propertiesWriterXML = new PropertiesWriterXML();
        propertiesWriterXML.setProperties(username, newUserModel.getUsername(), false);
        propertiesWriterXML.setProperties(password, newUserModel.getPassword(), false);

        RequestBody requestBody = RequestBody.create(gson.toJson(newUserModel), JSON);
        Request request = new Request.Builder()
                .url(PropertiesReader.getProperty("baseURL") + "v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        // System.out.println("User registration successful: " + response);
        String res = response.body().string();
        // System.out.println(res);
        if (response.isSuccessful()) {
            AuthResponseModel responseModel = gson.fromJson(res, AuthResponseModel.class);
            System.out.println("Response code is: " + response.code());
            System.out.println(responseModel.getToken());
            // PropertiesWriter.writeProperties("existingToken", responseModel.getToken(),false);
            propertiesWriterXML.setProperties(token, responseModel.getToken(), false);
            Assert.assertTrue(response.isSuccessful());
        } else {
            System.out.println("Error: " + response.code());
            ErrorModel errorModel = gson.fromJson(res, ErrorModel.class);
            System.out.println(errorModel.getStatus() + " " + errorModel.getError() + " " + errorModel.getMessage());
            Assert.assertFalse(response.isSuccessful());
        }
    }
}
