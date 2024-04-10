package okhttp;

import helpers.PropertiesReader;
import helpers.PropertiesReaderXML;
import helpers.TestConfig;
import models.ContactListModel;
import models.ContactModel;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetAllContacts implements TestConfig{

    @Test
    public void testGetAllContactsPositive() throws IOException {
        Request request=new Request.Builder()
                .url(PropertiesReader.getProperty("baseURL")+"v1/contacts")
                .addHeader(authHeader, PropertiesReaderXML.getProperty("token"))
                .build()
                ;
        Response response= client.newCall(request).execute();
        System.out.println("Response code: "+response.code());
        String responseBody = response.body().string();
        //System.out.println("Response body: "+responseBody.toString());
        Assert.assertTrue(response.isSuccessful());
        ContactListModel contactListModel=gson.fromJson(responseBody, ContactListModel.class);
        System.out.println(contactListModel.getContacts().size());
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
