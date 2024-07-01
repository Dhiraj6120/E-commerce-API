package Basic;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Builder {
    public RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com/api/ecom").setContentType(ContentType.JSON).build();
    }

    public RequestSpecification requestSpecification(String token){
        return new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom")
                .addHeader("authorization", token).build();
    }
}
