package nl.com.acs.integrationTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.equalTo;

import static io.restassured.RestAssured.given;

public class LoginIT extends BaseITTest{

    @Test
    void logon_withValidRequest(){

        String userLoginRequest= "{\n" +
                "    \"userName\":\"testUserName\",\n" +
                "    \"password\":\"test123\"\n" +
                "}";
        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .body(userLoginRequest)
                .contentType("application/json;charset=utf-8")
                .when()
                .log().all(true)
                .post(HTTP_LOCALHOST + PORT + CONTEXT + "/logon")
                .then()
                .log()
                .all(true)
                .statusCode(200)
                .body("status", equalTo("Success"));
    }
    @Test
    void logon_withInvalidUsername(){

        String userLoginRequest= "{\n" +
                "    \"userName\":\"Invalid\",\n" +
                "    \"password\":\"test123\"\n" +
                "}";
        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .body(userLoginRequest)
                .contentType("application/json;charset=utf-8")
                .when()
                .log().all(true)
                .post(HTTP_LOCALHOST + PORT + CONTEXT + "/logon")
                .then()
                .log()
                .all(true)
                .statusCode(500)
                .body("errorCode", equalTo("REF-F-ERROR-003"))
                .body("description", equalTo("Invalid username"));
    }
    @Test
    void logon_withInvalidPassword(){

        String userLoginRequest= "{\n" +
                "    \"userName\":\"testUserName\",\n" +
                "    \"password\":\"Invalid\"\n" +
                "}";
        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .body(userLoginRequest)
                .contentType("application/json;charset=utf-8")
                .when()
                .log().all(true)
                .post(HTTP_LOCALHOST + PORT + CONTEXT + "/logon")
                .then()
                .log()
                .all(true)
                .statusCode(500)
                .body("errorCode", equalTo("REF-F-ERROR-003"))
                .body("description", equalTo("Invalid username or password"));;
    }
    @Test
    void logon_withEmptyUserName(){

        String userLoginRequest= "{\n" +
                "    \"password\":\"Invalid\"\n" +
                "}";
        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .body(userLoginRequest)
                .contentType("application/json;charset=utf-8")
                .when()
                .log().all(true)
                .post(HTTP_LOCALHOST + PORT + CONTEXT + "/logon")
                .then()
                .log()
                .all(true)
                .statusCode(400)
                .body("errorCode[0]", equalTo("REF-F-ERROR-002"))
                .body("description[0]", equalTo("userName must not be null"));;
    }
}
