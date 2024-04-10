package restassured;

import helpers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.ContactListModel;
import models.ContactModel;
import models.ContactResponseModel;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class DeleteContactByID implements TestConfig{
    String id;
    @BeforeMethod
    public void precondition() throws Exception {
        RestAssured.baseURI= ADD_CONTACT_PATH;
        ContactModel contactModel=new ContactModel(NameAndLastNameGenerator.generateName()
                , NameAndLastNameGenerator.generateLastName()
                , EmailGenerator.generateEmail(10, 5, 3)
                , PhoneNumberGenerator.generatePhoneNumber()
                , AddressGenerator.generateAddress()
                , "Frend");

        ContactResponseModel responseModel = given()
                .body(contactModel)
                .contentType(ContentType.JSON)
                .header(authHeader, PropertiesReaderXML.getProperty(token))
                .when()
                .post()
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("added"))
                .extract()
                .as(ContactResponseModel.class);
        System.out.println(responseModel.getMessage());
        id = responseModel.getMessage().substring(responseModel.getMessage().lastIndexOf(" ") + 1);
       // System.out.println(id);
        DataBaseWriter dbw=new DataBaseWriter();
        dbw.contactDatabaseRecorder(id,contactModel);

    }
    @Test
    public void testDeleteContactByID() throws Exception {
        ContactModel contactModel= DataBaseReader.readContactFromDatabase(id);
        System.out.println("Contact id is: "+contactModel.getId());
        ContactResponseModel contactResponseModel = given()
                .header(authHeader, PropertiesReaderXML.getProperty(token))
                .when()
                .delete(baseURI + "/" + contactModel.getId())
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("deleted"))
                .extract()
                .as(ContactResponseModel.class);
        System.out.println("Response message: " + contactResponseModel.getMessage());
        id=contactModel.getId();
        System.out.println(id);
    }
    @AfterMethod
    public void postcondition() throws Exception {
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
            if (!(contact.getId().contentEquals(id))) {
                System.out.println("Contact by ID: " +id+ " not found");
            }
            Assert.assertNotEquals(contact.getId(), id);

        }
        printStream.close();


    }



}
