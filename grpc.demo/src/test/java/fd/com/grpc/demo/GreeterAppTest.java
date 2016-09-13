package fd.com.grpc.demo;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.GreeterGrpc;
import io.grpc.examples.GreeterOuterClass;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexf on 28-Jan-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GreeterApp.class)
@WebIntegrationTest

public class GreeterAppTest {
    private ManagedChannel channel;
    @Before
    public void setup(){
        channel = ManagedChannelBuilder.forAddress("localhost", 6666)
                .usePlaintext(true)
                .build();

    }
    @After
    public void tearDown(){
        channel.shutdown();
    }

    @Test
    public  void simpleGreeting () throws ExecutionException, InterruptedException {

        final GreeterGrpc.GreeterFutureStub greeterFutureStub = GreeterGrpc.newFutureStub(channel);
        final GreeterOuterClass.HelloRequest helloRequest = GreeterOuterClass.HelloRequest.newBuilder().setName("John").build();
        final String replay = greeterFutureStub.sayHello(helloRequest).get().getMessage();
        System.out.println(replay);
    }

    @Test
    public  void actuatorTest () throws ExecutionException, InterruptedException {
        final TestRestTemplate template = new TestRestTemplate();

        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/env",String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
