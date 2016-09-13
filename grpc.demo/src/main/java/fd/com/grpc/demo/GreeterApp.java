/**
 * 
 */
package fd.com.grpc.demo;

/**
 * @author Ares
 *
 */
import io.grpc.examples.GreeterGrpc;
import io.grpc.examples.GreeterOuterClass;
import io.grpc.stub.StreamObserver;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@EnableDiscoveryClient
@ComponentScan(value = {"fd.com.grpc.demo", "io.grpc.examples"})
public class GreeterApp {

    @GRpcService(grpcServiceOuterClass = GreeterGrpc.class)
    public static class GreeterService implements GreeterGrpc.Greeter{
        @Override
        public void sayHello(GreeterOuterClass.HelloRequest request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
            final GreeterOuterClass.HelloReply.Builder replyBuilder = GreeterOuterClass.HelloReply.newBuilder().setMessage("Hello " + request.getName());
            responseObserver.onNext(replyBuilder.build());
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) {
    	System.setProperty("spring.config.name", "grpc-server");
        SpringApplication.run(GreeterApp.class,args);
    }
}
