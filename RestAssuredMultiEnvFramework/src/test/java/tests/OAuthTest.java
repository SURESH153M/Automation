package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OAuthTest {
    public static void main(String[] args) {
        String env = System.getProperty("env", "dev");
        ConfigReader config = new ConfigReader(env);

        RestAssured.baseURI = config.getProperty("base.url");

        String token = given()
                .formParam("client_id", config.getProperty("client.id"))
                .formParam("client_secret", config.getProperty("client.secret"))
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when()
                .post("/oauthapi/oauth2/resourceOwner/token")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");

        Response res = given()
                .queryParam("access_token", token)
                .when()
                .get("/oauthapi/getCourseDetails")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(res.asPrettyString());
    }
}
