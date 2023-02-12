import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;


public class ReqresInTests {

    @Test
    public void singleUserTest() {
        // with soft asserts
        given().
                log().uri().
        when().
                get("https://reqres.in/api/users/2").
        then().
                statusCode(200).
                and().
                body("data.id", is(2),
                        "data.email", is("janet.weaver@reqres.in"),
                        "data.first_name", is("Janet"),
                        "data.last_name", is("Weaver"),
                        "data.avatar", is(notNullValue()));
    }

    @Test
    public void singleUserNotFoundTest() {
        String response =
        given().
                log().uri().
        when().
                get("https://reqres.in/api/users/23").
        then().
                statusCode(404).
//                body("isEmpty()", Matchers.is(true)). // maybe
//                body("$", Matchers.empty()). // no
//                body("", Matchers.empty()). // no
                extract().response().asString();

        assertThat(response).isEqualTo("{}");
    }

    @Test
    public void createUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given().
                log().uri().
                contentType(JSON).
                body(data).
        when().
                post("https://reqres.in/api/users").
        then().
                log().status().
                log().body().
                statusCode(201).
                body("name", is("morpheus"),
                        "job", is("leader"),
                        "id", is(notNullValue()),
                        "createdAt", is(notNullValue()));
    }

    @Test
    public void putUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given().
                log().uri().
                contentType(JSON).
                body(data).
        when().
                put("https://reqres.in/api/users/2").
        then().
                log().status().
                log().body().
                statusCode(200).
                body("name", is("morpheus"),
                        "job", is("zion resident"),
                        "updatedAt", is(notNullValue()));
    }

    @Test
    public void patchUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"salesman\" }";

        given().
                log().uri().
                contentType(JSON).
                body(data).
        when().
                patch("https://reqres.in/api/users/2").
        then().
                log().status().
                log().body().
                statusCode(200).
                body("name", is("morpheus"),
                        "job", is("salesman"),
                        "updatedAt", is(notNullValue()));
    }

    @Test
    public void deleteUserTest() {
        given().
                log().uri().
        when().
                delete("https://reqres.in/api/users/2").
        then().
                log().status().
                log().body().
                statusCode(204).
                body(blankString());
    }

}
