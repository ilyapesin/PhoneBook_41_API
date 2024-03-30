package okhttp;

import helpers.PropertiesReader;
import helpers.TestConfig;
import models.ContactListModel;
import models.ContactModel;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetAllContacts {

    @Test
    public void testGetAllContactsPositive() throws IOException {
        Request request=new Request.Builder()
                .url(PropertiesReader.getProperty("baseURL")+"v1/contacts")
                .addHeader("Authorization",PropertiesReader.getProperty("existingToken"))
                .build()
                ;
        Response response= TestConfig.client.newCall(request).execute();
        String responseBody = response.body().string();
        System.out.println("Response body: "+responseBody.toString());
        Assert.assertTrue(response.isSuccessful());
        ContactListModel contactListModel=TestConfig.gson.fromJson(responseBody, ContactListModel.class);
        for(ContactModel contactModel : contactListModel.getContacts()) {
            System.out.println(contactModel.getId());
            System.out.println(contactModel.getName());
            System.out.println(contactModel.getLastName());
            System.out.println(contactModel.getEmail());
            System.out.println(contactModel.getPhone());
            System.out.println(contactModel.getAddress());
            System.out.println(contactModel.getDescription());
            System.out.println("================================================================");


        }

    }

}
