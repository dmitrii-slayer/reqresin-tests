import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
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
}
