package restassured;

import helpers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.NewUserModel;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class RegistrationTest implements TestConfig {
    @Test
    public void registrationTest() throws IOException {
        RestAssured.baseURI= PropertiesReader.getProperty("baseURL")+"v1/user/registration/usernamepassword";
        NewUserModel newUserModel= NewUserModel.builder()
                .username(EmailGenerator.generateEmail(10,5,3))
                .password(PasswordStringGenerator.generateString())
                .build();
        Object token = given()
                .body(newUserModel)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("token");
        System.out.println("Token: " + token);


    }
}
