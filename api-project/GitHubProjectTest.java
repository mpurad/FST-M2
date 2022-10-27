package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHubProjectTest {

    RequestSpecification requestSpec;

    String sshKey;

    int id;

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                // Set content type
                .setContentType(ContentType.JSON)
                // Set base URL
                .setBaseUri("https://api.github.com/user/keys")
                // Build request specification
               .build().auth().oauth2("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");
    }

    @Test(priority=1)
    public void postRequestTest(){

        String reqBody = "{\n" +
                "    \"title\": \"TestAPIKey\",\n" +
                "    \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCBhp1V/lmDJ25fs5s7aRDEQGeqmEKN3kFPG90VKLDfb7F5ENUUnt1ejm7L/SxmcP4zPcT43hOI+QTjNSaD1pOkYV3E4Ly6bkN4be1+fJt9iazZEh3JH+2pr7qQKmC6y4jmz4fuQT0G/PxAJ3oUfYeWl7YDkStrZxmqY8Bz6kwr0GGG1ArnSGOh/vWmmoe3tTgAzBVG6iNLfZt3IeYt8jHJjW8Ww4PdTi2nDLJq77L71c8v6K5HI2i0mFnsxFcb09O8zIoVzitZjFsXzySzRe3Py3Y+vaefUcVzKVn3STGbuYrWmXZzLVfumD3YWOLRfsf2sef5fh/fg67rSihwTmab\"\n" +
                "}";

        Response response = given().spec(requestSpec).body(reqBody)
                .when().post();

        System.out.println("Response :" + response.asPrettyString());

        id = response.then().extract().path("id");

        response.then().statusCode(201);
    }

    @Test(priority=2)
    public void getRequestTest(){
        Response response =
                given().spec(requestSpec) // Set headers
                        .when().pathParam("id", id) // Set path parameter
                        .get("/{id}"); // Send GET request

        // Assertion
        System.out.println("Response :" + response.asPrettyString());
        Reporter.log(response.asString());
        response.then().statusCode(200);
    }

    @Test(priority=3)
    public void deleteRequestTest(){
        Response response =
                given().spec(requestSpec) // Set headers
                        .when().pathParam("id", id) // Set path parameter
                        .delete("/{id}"); // Send DELETE request

        // Assertion
        Reporter.log(response.asString());
        response.then().statusCode(204);
    }
}
