package guru.qa;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class Specs {
    // using builder
    public static RequestSpecification requestSpecWithData = new RequestSpecBuilder()
            // baseUri и basePath вынес в setUp()
//            .setBaseUri("https://reqres.in")
//            .setBasePath("/api")
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .addHeader("Connection", "close")
            .log(LogDetail.ALL)
            .build();

    // using with()
    public static RequestSpecification requestSpecNoData = with()
            // baseUri и basePath вынес в setUp()
//            .baseUri("https://reqres.in")
//            .basePath("/api")
            .accept(ContentType.JSON)
            .header("Connection", "close")
            .log().method()
            .log().uri();

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .expectStatusCode(200)
//            .expectBody(containsString("success"))
            .build();
}
