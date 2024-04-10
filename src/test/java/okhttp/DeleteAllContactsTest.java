package okhttp;

import helpers.PropertiesReader;
import helpers.PropertiesReaderXML;
import helpers.TestConfig;
import models.ContactListModel;
import models.ContactModel;
import models.ContactResponseModel;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DeleteAllContactsTest implements TestConfig {
    int size=0;
    @BeforeTest
    public void preconditions() throws Exception {
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
        size = contactListModel.getContacts().size();
        while (size<3) {
            AddNewContactTest test = new AddNewContactTest();
            test.testAddNewContact();
            size++;
        }
        System.out.println("Size: " + size);
    }
    @Test
    public void deleteAllContactsTest() throws Exception {
        Request request = new Request.Builder()
                .url(ADD_CONTACT_PATH+"/clear")
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
