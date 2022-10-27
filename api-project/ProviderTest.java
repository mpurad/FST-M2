package liveProject;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("UserProvider")
@PactFolder("target/pacts")
public class ProviderTest {

    @BeforeEach
    public void setup(PactVerificationContext context){
        HttpTestTarget target = new HttpTestTarget("localhost", 8585);
        context.setTarget(target);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void providerTest(PactVerificationContext context){
        context.verifyInteraction();
    }

    @State("A request to create a user")
    public void state1(){

    }

//    @State("A request to get a user")
//    public void state2(){
//
//    }
//
//    @State("A request to delete a user")
//    public void state3(){
//
//    }
}
