package Tests;

import Basic.Builder;
import POJO.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceTest {

    LoginResponse loginResponse;
    CreateProductResponse createProductResponse;
    CreateOrderResponse createOrderResponse;
    String token= null;
    @Test
    public void getLogInToken(){

        //Login to application and Generate the token
        RequestSpecification req1 = new Builder().requestSpecification();
        LoginRequest lR = new LoginRequest();
        lR.setUserEmail("asd1209@yopmail.com");
        lR.setUserPassword("Dhiali@7936");
        RequestSpecification reqLogIn = given().spec(req1).body(lR);
        loginResponse = reqLogIn.when().post("/auth/login")
                .then().log().all().assertThat().statusCode(200).extract().response().as(LoginResponse.class);

        token = loginResponse.getToken();

        //Create a product
        RequestSpecification req2 = new Builder().requestSpecification(token);
        RequestSpecification createProductRes = given().spec(req2)
                .param("productName", "Sonoma 1121 pro")
                .param("productAddedBy", loginResponse.getUserId())
                .param("productCategory", "Electronic")
                .param("productSubCategory", "Computers")
                .param("productPrice", "120121")
                .param("productDescription", "Macbook Apple product")
                .param("productFor", "Everyone")
                .multiPart("productImage", new File("/Users/dhiraj/Documents/Study/End to End ecommerce API/src/main/resources/img.png"));

        createProductResponse = createProductRes.when().post("/product/add-product").
                then().log().all().assertThat().statusCode(201).extract().as(CreateProductResponse.class);

        //Creating the order with created product (Placing the order)

        RequestSpecification req3 = new Builder().requestSpecification(token, "JSON");
        CreateOrderRequest COR = new CreateOrderRequest();

        OrderDetails OD = new OrderDetails();
        List<OrderDetails> orders = new ArrayList<>();
        OD.setCountry("India");
        OD.setProductOrderedId(createProductResponse.getProductId());
        orders.add(OD);
        COR.setOrders(orders);

        RequestSpecification createOrderRes = given().spec(req3).body(COR);
        createOrderResponse = createOrderRes.when().post("/order/create-order")
                .then().log().all().assertThat().statusCode(201).extract().response().as(CreateOrderResponse.class);

        RequestSpecification deleteRequestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom")
                .addHeader("authorization", token).build();

        given().spec(deleteRequestSpecification).pathParam("productId", createProductResponse.getProductId())
                .when().delete("/product/delete-product/{productId}").then().log().all().assertThat().statusCode(200);
    }
}
