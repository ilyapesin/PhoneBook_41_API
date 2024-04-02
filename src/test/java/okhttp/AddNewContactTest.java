package okhttp;

import helpers.*;
import models.ContactModel;
import models.ContactResponseModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddNewContactTest implements TestConfig{
    String id;
    @Test
    public void testAddNewContact() throws Exception {
        ContactModel contactModel = new ContactModel(NameAndLastNameGenerator.generateName()
                , NameAndLastNameGenerator.generateLastName()
                , EmailGenerator.generateEmail(10, 5, 3)
                , PhoneNumberGenerator.generatePhoneNumber()
                , AddressGenerator.generateAddress()
                , "Frend");
        System.out.println(contactModel.getName()+","+contactModel.getLastName()+","+contactModel.getEmail());


        RequestBody requestBody = RequestBody.create(gson.toJson(contactModel), JSON);
        Request request = new Request.Builder()
                .url(TestConfig.ADD_CONTACT_PATH)
                .addHeader(authHeader, PropertiesReaderXML.getProperty(token))
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        ContactResponseModel contactResponseModel = gson.fromJson(response.body().string(), ContactResponseModel.class);
        String message = contactResponseModel.getMessage();
        System.out.println("Message is: "+message);
        System.out.println(response.code());
        id = message.substring(message.lastIndexOf(" ") + 1);
        System.out.println("ID is : " + id);
        Assert.assertTrue(response.isSuccessful());
    }
    @Test
    public void deleteNewContactTest() throws Exception {
        testAddNewContact();

        Request request = new Request.Builder()
                .url(ADD_CONTACT_PATH+"/"+id)
                .addHeader(authHeader, PropertiesReaderXML.getProperty("token"))
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        ContactResponseModel contactResponseModel  = gson
                .fromJson(response.body().string(), ContactResponseModel.class);
        System.out.println("Message : "+contactResponseModel.getMessage());
        Assert.assertTrue(response.isSuccessful());


    }
}


