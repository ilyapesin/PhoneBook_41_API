package restassured;

import helpers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.ContactListModel;
import models.ContactModel;
import models.ContactResponseModel;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpdateContactByID implements TestConfig{
    String id;
    ContactModel contactModel;
    @BeforeMethod
    public void beforeMethod() throws SQLException {
        RestAssured.baseURI= ADD_CONTACT_PATH;
        contactModel=new ContactModel(NameAndLastNameGenerator.generateName()
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
      //  System.out.println(responseModel.getMessage());
        id = responseModel.getMessage().substring(responseModel.getMessage().lastIndexOf(" ") + 1);
      //  System.out.println(id);
        DataBaseWriter dbw=new DataBaseWriter();
        dbw.contactDatabaseRecorder(id,contactModel);

    }
    @Test
    public void updateContactTest() throws Exception {
        contactModel=DataBaseReader.readContactFromDatabase(id);
        String oldEmail=contactModel.getEmail();
        contactModel.setEmail(EmailGenerator.generateEmail(7,5,3));
        given()
                .header(authHeader, PropertiesReaderXML.getProperty(token))
                .body(contactModel)
                .contentType(ContentType.JSON)
                .when()
                .put()
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .assertThat().body("message", containsString("updated"));
        //System.out.println(message);
        Assert.assertNotEquals(oldEmail,contactModel.getEmail());

    }
    @AfterMethod
    public void afterMethod() throws Exception {

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

            if (contact.getId().contentEquals(id)) {
                System.out.println(contact.getId());
                System.out.println(contact.getName());
                System.out.println(contact.getLastName());
                System.out.println(contact.getEmail());
                System.out.println("--------------------------------");
                System.out.println("Contact was updated! ID: " +id+ " and found");
                Assert.assertEquals(contact.getId(), id);
                break;
            }



        }
        printStream.close();


    }

}
