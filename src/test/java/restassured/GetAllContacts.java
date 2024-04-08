package restassured;

import helpers.PropertiesReader;
import helpers.PropertiesReaderXML;
import helpers.TestConfig;
import io.restassured.RestAssured;
import models.ContactListModel;
import models.ContactModel;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class GetAllContacts implements TestConfig {
    @Test
    public void testGetAllContacts() throws IOException {
       // RestAssured.baseURI= PropertiesReader.getProperty("baseURL")+"v1/contacts";
        ContactListModel contactListModel = given()
                .header(authHeader, PropertiesReaderXML.getProperty(token))
                .when()
                .get(PropertiesReader.getProperty("baseURL") + "v1/contacts")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(ContactListModel.class);
        for(ContactModel contact : contactListModel.getContacts()) {
            System.out.println(contact.getId());
            System.out.println(contact.getName());
            System.out.println(contact.getLastName());
            System.out.println(contact.getEmail());
            System.out.println("--------------------------------");


        }


    }
}
