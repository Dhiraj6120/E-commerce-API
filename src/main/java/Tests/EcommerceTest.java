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

        //Create a product
        RequestSpecification req2 = new Builder().requestSpecification(loginResponse.getToken());
        RequestSpecification createProductRes = given().spec(req2)
                .param("productName", "Macbook 16 pro")
                .param("productAddedBy", loginResponse.getUserId())
                .param("productCategory", "Electronic")
                .param("productSubCategory", "Computers")
                .param("productPrice", "120121")
                .param("productDescription", "Macbook Apple product")
                .param("productFor", "Everyone")
                .multiPart("productImage", new File("/Users/dhiraj/Documents/Study/End to End ecommerce API/src/main/resources/img.png"));

        createProductRes.when().post("/product/add-product").
                then().log().all().assertThat().statusCode(201);

    }
}
