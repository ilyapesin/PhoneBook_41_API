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

import java.io.IOException;

public class LoginTest implements TestConfig{
    @Test
    public void loginTest() throws IOException {
        AuthRequestModel requestModel = AuthRequestModel
                .username(PropertiesReader.getProperty("existingUserEmail"))
                .password(PropertiesReader.getProperty("existingUserPassword"));

        RequestBody requestBody = RequestBody.create(gson.toJson(requestModel),JSON);
        Request request = new Request.Builder()
                .url(PropertiesReader.getProperty("baseURL")+"v1/user/login/usernamepassword")
                .post(requestBody)
                .build();
        Response response=client.newCall(request).execute();

        if(response.isSuccessful()) {
            AuthResponseModel responseModel =gson.fromJson(response.body().string(), AuthResponseModel.class);
            System.out.println("Response code is: " + response.code());
            System.out.println(responseModel.getToken());
            PropertiesWriter.writeProperties("existingToken", responseModel.getToken(),false);
            Assert.assertTrue(response.isSuccessful());
        }else {
            System.out.println("Error: " + response.code());
            ErrorModel errorModel = gson.fromJson(response.body().string(), ErrorModel.class);
            System.out.println(errorModel.getStatus() + " " + errorModel.getError()+" "+errorModel.getMessage());
            Assert.assertFalse(response.isSuccessful());
        }
    }
    @Test
    public void loginTestReadXML() throws Exception {
        AuthRequestModel requestModel = AuthRequestModel
                .username(PropertiesReaderXML.getProperty("username"))
                .password(PropertiesReaderXML.getProperty("password"));
        RequestBody requestBody = RequestBody.create(gson.toJson(requestModel), JSON);
        Request request = new Request.Builder()
                .url(PropertiesReader.getProperty("baseURL")+"v1/user/login/usernamepassword")
                .post(requestBody)
                .build();
        Response response=client.newCall(request).execute();

        if(response.isSuccessful()) {
            AuthResponseModel responseModel =gson.fromJson(response.body().string(), AuthResponseModel.class);
            System.out.println("Response code is: " + response.code());
            System.out.println(responseModel.getToken());
            PropertiesWriterXML propertiesWriterXML=new PropertiesWriterXML();
            propertiesWriterXML.setProperties("token", responseModel.getToken(),false);
            Assert.assertTrue(response.isSuccessful());
        }else {
            System.out.println("Error: " + response.code());
            ErrorModel errorModel = gson.fromJson(response.body().string(), ErrorModel.class);
            System.out.println(errorModel.getStatus() + " " + errorModel.getError()+" "+errorModel.getMessage());
            Assert.assertFalse(response.isSuccessful());
        }


    }
}
