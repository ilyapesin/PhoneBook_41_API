package restassured;

import helpers.PropertiesReader;
import helpers.PropertiesReaderXML;
import helpers.PropertiesWriterXML;
import helpers.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.NewUserModel;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class LoginTest implements TestConfig {
    @Test
    public void loginTest() throws IOException {

        RestAssured.baseURI = PropertiesReader.getProperty("baseURL") + "v1/user/login/usernamepassword";
        AuthRequestModel authRequestModel=new AuthRequestModel((PropertiesReaderXML.getProperty(username)),
                (PropertiesReaderXML.getProperty(password)));

        AuthResponseModel authResponseModel = given()
                .body(authRequestModel)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(AuthResponseModel.class);

        PropertiesWriterXML propertiesWriterXML=new PropertiesWriterXML();
        propertiesWriterXML.setProperties(token, authResponseModel.getToken(),false);
       // System.out.println("Token: " + authResponseModel.getToken());


    }


}
