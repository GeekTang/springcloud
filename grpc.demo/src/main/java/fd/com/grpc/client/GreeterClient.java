/**
 * 
 */
package fd.com.grpc.client;

/**
 * @author Ares
 *
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@EnableDiscoveryClient
@ComponentScan(value = {"fd.com.grpc.client"})
public class GreeterClient {

    public static void main(String[] args) {
    	System.setProperty("spring.config.name", "grpc-client");
        SpringApplication.run(GreeterClient.class,args);
    }
}
