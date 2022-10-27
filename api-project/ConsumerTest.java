package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Headers
    Map<String, String> headers = new HashMap<>();
    //Resource path
    String resourcePath = "/api/users";
    //Create the contract
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        //Set the headers
        headers.put("Content-Type", "application/json");
        //Create the body
        DslPart requestResposeBody = new PactDslJsonBody()
                .numberType("id", 123)
                .stringType("firstName", "FirstNameTest")
                .stringType("lastName", "LastNameTest")
                .stringType("email","abc@test.com");

        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .method("POST")
                .headers(headers)
                .path(resourcePath)
                .body(requestResposeBody)
                .willRespondWith()
                .status(201)
                .body(requestResposeBody)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")
    public void consumerTest(){
        String baseUri = "http://localhost:8282" + resourcePath;

        //Request body
        Map<String, Object> reqBody = new HashMap<>();

        reqBody.put("id", 123);
        reqBody.put("firstName", "FirstNameTest");
        reqBody.put("lastName", "LastNameTest");
        reqBody.put("email","abc@test.com");

        //Generate Response
        given().headers(headers).body(reqBody).log().all()
                .when().post(baseUri)
                .then().statusCode(201).log().all();
    }
}
