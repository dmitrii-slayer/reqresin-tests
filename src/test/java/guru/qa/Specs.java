package guru.qa;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class Specs {
    public static RequestSpecification requestSpecWithData = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().all()
            .contentType(ContentType.JSON);

    public static RequestSpecification requestSpecGet = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().method()
            .log().uri();

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .expectStatusCode(200)
//            .expectBody(containsString("success"))
            .build();
}
