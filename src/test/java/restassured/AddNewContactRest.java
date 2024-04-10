package restassured;

import helpers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.ContactModel;
import models.ContactResponseModel;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class AddNewContactRest implements TestConfig {
    @Test
    public void testAddNewContact() throws Exception {
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
        String id = responseModel.getMessage().substring(responseModel.getMessage().lastIndexOf(" ") + 1);
        System.out.println(id);
        DataBaseWriter dbw=new DataBaseWriter();
        dbw.contactDatabaseRecorder(id,contactModel);




    }
}
