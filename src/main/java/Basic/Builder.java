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

    public RequestSpecification requestSpecification(String token, String JSON){
        if(JSON.equalsIgnoreCase("jSON")){
        return new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom")
                .addHeader("authorization", token).setContentType(ContentType.JSON).build();
        }
        else {
            return new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom")
                    .addHeader("authorization", token).addQueryParam("id", JSON).build();
        }

    }
}
