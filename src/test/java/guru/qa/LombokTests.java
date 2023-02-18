package guru.qa;

import org.junit.jupiter.api.Test;
import guru.qa.models.UserData;
import guru.qa.models.User;

import static guru.qa.Specs.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.assertThat;

public class LombokTests {


    @Test
    public void getSingleUserTest() {
        // with soft asserts (assertAll)
        // request spec in given()
        UserData user =
        given(requestSpecGet).
        when().
                get("/users/2").
        then().
                spec(responseSpec).
                log().body().
//                statusCode(200).
                and().
                extract().as(UserData.class);

        User singleUser = user.getUser();

        assertAll("get user info",
                () -> assertEquals("janet.weaver@reqres.in", singleUser.getEmail()),
                () -> assertEquals("Janet", singleUser.getFirstName()),
                () -> assertEquals("Weaver", singleUser.getLastName()),
                () -> assertNotNull(singleUser.getAvatarLink())
        );

//        assertThat(user.getUser().getFirstName()).isEqualTo("Janet");

    }

    @Test
    public void singleUserNotFoundTest() {
        // request spec in spec()
        String response =
                given().
                        spec(requestSpecGet).
                when().
                        get("/users/23").
                then().
                        statusCode(404).
                        extract().response().asString();

        assertEquals("{}", response); // JUnit assertion
//        assertThat(response).isEqualTo("{}"); // AssertJ assertion
    }

    @Test
    public void createUserTest() {
        // prepare user
        String name = "morpheus";
        String job = "leader";
        User user = new User();
        user.setName(name);
        user.setJob(job);

        User createdUser =
        given().
                spec(requestSpecWithData).
                body(user).
        when().
                post("/users").
        then().
                log().status().
                log().body().
                statusCode(201).
                extract().as(User.class);

        assertAll("created user info",
                () -> assertTrue(createdUser.getId() > 0),
                () -> assertEquals(name, createdUser.getName()),
                () -> assertEquals(job, createdUser.getJob()),
                () -> assertNotNull(createdUser.getCreatedAt())
        );
    }

    @Test
    public void putUserTest() {
        // prepare user
        String name = "morpheus";
        String job = "zion resident";
        User user = new User();
        user.setName(name);
        user.setJob(job);

        given().
                spec(requestSpecWithData).
                body(user).
        when().
                put("/users/2").
        then().
                log().status().
                log().body().
                statusCode(200).
                body("name", is(name),
                        "job", is(job),
                        "updatedAt", is(notNullValue()));
    }

    @Test
    public void patchUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"salesman\" }";

        given().
                spec(requestSpecWithData).
                body(data).
        when().
                patch("/users/2").
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
