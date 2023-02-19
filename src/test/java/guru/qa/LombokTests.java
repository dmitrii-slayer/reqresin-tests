package guru.qa;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import guru.qa.models.UserData;
import guru.qa.models.User;

import static guru.qa.Specs.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.assertThat;

public class LombokTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void getSingleUserTest() {
        // with soft asserts (assertAll)
        // request spec in given()
        UserData userData =
        given(requestSpecNoData).
        when().
                get("/users/2").
        then().
                spec(responseSpec).
                log().body().
//                statusCode(200).
                and().
                extract().as(UserData.class);

        User singleUser = userData.getUser();

        assertAll("get single user info",
                () -> assertEquals(2, singleUser.getId()),
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
                        spec(requestSpecNoData).
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
        // prepare user
        String name = "morpheus";
        String job = "salesman";
        User user = new User();
        user.setName(name);
        user.setJob(job);

        given().
                spec(requestSpecWithData).
                body(user).
        when().
                patch("/users/2").
        then().
                spec(responseSpec).
                log().body().
                statusCode(200).
                body("name", is(name),
                        "job", is(job),
                        "updatedAt", is(notNullValue()));
    }

    @Test
    public void deleteUserTest() {
        given().
                spec(requestSpecNoData).
        when().
                delete("/users/2").
        then().
                log().status().
                log().ifValidationFails(LogDetail.ALL).
                statusCode(204).
                body(blankString());
    }

}
