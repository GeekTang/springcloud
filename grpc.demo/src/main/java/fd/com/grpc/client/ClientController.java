/**
 * 
 */
package fd.com.grpc.client;

import java.util.concurrent.ExecutionException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.GreeterGrpc;
import io.grpc.examples.GreeterOuterClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jasontang
 *
 */
@RestController
public class ClientController {
	
	@Autowired
	private LoadBalancerClient lbClient;

    
    @RequestMapping(value = "/greet", method = RequestMethod.GET)
    public String getDate(){
    	ServiceInstance instance = lbClient.choose("grpc-service");
    	String host = instance.getHost();
    	int port = instance.getPort();
    	ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();
        final GreeterGrpc.GreeterFutureStub greeterFutureStub = GreeterGrpc.newFutureStub(channel);
        final GreeterOuterClass.HelloRequest helloRequest = GreeterOuterClass.HelloRequest.newBuilder().setName("John").build();
        String replay = "";
		try {
			replay = greeterFutureStub.sayHello(helloRequest).get().getMessage();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return replay;
    }
}
