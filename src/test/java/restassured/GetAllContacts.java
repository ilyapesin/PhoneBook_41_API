package restassured;

import helpers.PropertiesReader;
import helpers.PropertiesReaderXML;
import helpers.TestConfig;
import io.restassured.RestAssured;
import models.ContactListModel;
import models.ContactModel;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GetAllContacts implements TestConfig {

    @BeforeTest
    public void preconditions() throws Exception {
        RestAssured.baseURI= ADD_CONTACT_PATH;
    }
    @Test
    public void testGetAllContacts() throws IOException {
        File logfile=new File("src/logs/testres.log");
        if (!logfile.exists()) {
            logfile.getParentFile().mkdirs();
            logfile.createNewFile();

        }
        PrintStream printStream=new PrintStream(new FileOutputStream(logfile));
        System.setOut(printStream);
        System.setErr(printStream);

        ContactListModel contactListModel = given()
                .header(authHeader, PropertiesReaderXML.getProperty(token))
                .when()
                .get(baseURI)
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
        printStream.close();


    }
}
