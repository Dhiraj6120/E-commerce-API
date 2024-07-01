package Tests;

import Basic.Builder;
import POJO.LoginRequest;
import POJO.LoginResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class EcommerceTest {

    LoginResponse loginResponse;
    @Test
    public void getLogInToken(){

        //Login to application and Generate the token
        RequestSpecification req1 = new Builder().requestSpecification();
        LoginRequest lR = new LoginRequest();
        lR.setUserEmail("dhiraj45@yopmail.com");
        lR.setUserPassword("Dhiali@7936");
        RequestSpecification reqLogIn = given().spec(req1).body(lR);
        loginResponse = reqLogIn.when().post("/auth/login")
                .then().log().all().assertThat().statusCode(200).extract().response().as(LoginResponse.class);


    }
}
