package oauth2.ResourceServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class ResourceServerApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(ResourceServerApp.class, args);
    }
}
