package guru.qa;

import org.junit.jupiter.api.Test;
import guru.qa.models.UserData;
import guru.qa.models.User;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static guru.qa.Specs.requestSpec;
import static guru.qa.Specs.responseSpec;
import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.assertThat;

public class LombokTests {


    @Test
    public void getSingleUserTest() {
        // with soft asserts (assertAll)
        // request spec in given()
        UserData user =
        given(requestSpec).
        when().
                get("/users/2").
        then().
                spec(responseSpec).
                log().body().
//                statusCode(200).
                and().
                extract().as(UserData.class);

        User singleUser = user.getUser();

        assertAll("user info",
                () -> assertEquals("janet@reqres", singleUser.getEmail()),
                () -> assertEquals("my name", singleUser.getFirstName()),
                () -> assertEquals("asd", singleUser.getLastName()),
                () -> assertNotNull(singleUser.getAvatarLink())
        );

//        assertThat(user.getUser().getFirstName()).isEqualTo("Janet");

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

        assertEquals("{}", response); // JUnit assertion
//        assertThat(response).isEqualTo("{}"); // AssertJ assertion
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
