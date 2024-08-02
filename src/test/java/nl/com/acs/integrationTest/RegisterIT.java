package nl.com.acs.integrationTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class RegisterIT extends BaseITTest{

    @Test
    void register_withValidRequest(){

        String registerRequest ="{\n" +
                "   \"givenName\":\"TestName\",\n" +
                "   \"nameInitial\" : \"T\",\n" +
                "   \"surname\":\"TestSurName\",\n" +
                "   \"birthDate\": \"1987-01-22\",\n" +
                "   \"userName\": \"testUser\",\n" +
                "   \"address\": {\n" +
                        "\"street\":\"testStreet\",\n" +
                        "\"houseNumber\":\"123\",\n" +
                        "\"postalCode\":\"1234AB\",\n" +
                        " \"city\":\"Utrecht\",\n" +
                        " \"country\":\"NL\"\n" +
                    "},\n"+
                "   \"document\": {\n" +
                        "\"documentNumber\":\"234\",\n" +
                        "\"documentIssueDate\":\"2020-01-22\",\n" +
                        "\"documentTypeCode\":\"PASSPORT\",\n" +
                        "\"documentIssueCountry\":\"NL\"\n" +
                "}\n"
                + "}";
        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .body(registerRequest)
                .contentType("application/json;charset=utf-8")
                .when()
                .log().all(true)
                .post(HTTP_LOCALHOST + PORT + CONTEXT + "/register")
                .then()
                .log()
                .all(true)
                .statusCode(200)
                .body("userName", equalTo("testUser"))
                .body("password", notNullValue());
    }
    @Test
    void register_withUsernameAlreadyTaken(){

        String registerRequest ="{\n" +
                "   \"givenName\":\"TestName\",\n" +
                "   \"nameInitial\" : \"T\",\n" +
                "   \"surname\":\"TestSurName\",\n" +
                "   \"birthDate\": \"1987-01-22\",\n" +
                "   \"userName\": \"testUserName\",\n" +
                "   \"address\": {\n" +
                        "\"street\":\"testStreet\",\n" +
                        "\"houseNumber\":\"123\",\n" +
                        "\"postalCode\":\"1234AB\",\n" +
                        " \"city\":\"Utrecht\",\n" +
                        " \"country\":\"NL\"\n" +
                "},\n"+
                "   \"document\": {\n" +
                        "\"documentNumber\":\"234\",\n" +
                        "\"documentIssueDate\":\"2020-01-22\",\n" +
                        "\"documentTypeCode\":\"PASSPORT\",\n" +
                        "\"documentIssueCountry\":\"NL\"\n" +
                "}\n"
                + "}";

        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .body(registerRequest)
                .contentType("application/json;charset=utf-8")
                .when()
                .log().all(true)
                .post(HTTP_LOCALHOST + PORT + CONTEXT + "/register")
                .then()
                .log()
                .all(true)
                .statusCode(409)
                .body("errorCode", equalTo("REF-F-ERROR-001"))
                .body("description", equalTo("Username already taken"));
    }
    @Test
    void test_validation(){

        String registerRequest ="{\n" +
                "   \"nameInitial\" : \"T\",\n" +
                "   \"surname\":\"TestSurName\",\n" +
                "   \"birthDate\": \"1987-01-22\",\n" +
                "   \"userName\": \"TE\",\n" +
                "   \"address\": {\n" +
                        "\"street\":\"testStreet\",\n" +
                        "\"houseNumber\":\"123\",\n" +
                        " \"city\":\"Utrecht\",\n" +
                        " \"country\":\"NL\"\n" +
                "},\n"+
                "   \"document\": {\n" +
                        "\"documentIssueDate\":\"2020-01-22\",\n" +
                        "\"documentTypeCode\":\"PASSPORT\",\n" +
                        "\"documentIssueCountry\":\"NL\"\n" +
                "}\n"
                + "}";

        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .body(registerRequest)
                .contentType("application/json;charset=utf-8")
                .when()
                .log().all(true)
                .post(HTTP_LOCALHOST + PORT + CONTEXT + "/register")
                .then()
                .log()
                .all(true)
                .statusCode(400);

    }
}
