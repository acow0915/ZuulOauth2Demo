package getway.GetWayServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GetWayApp 
{
	// 使用方式 postman
	// 1. post http://localhost:8888/proxy/OauthApi/oauth/token?grant_type=password&scope=select&client_id=clientId1
	// 2. 設定 Authorization : Username = clientId1, Password = 123qwe
	// 3. 設定 body username=tim,  password=123qwe
	
	public static void main( String[] args ) {
        SpringApplication.run(GetWayApp.class, args);
    }
	
	@Bean
	public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory) {
	    return factory.getUserInfoRestTemplate();
	}
	
//	@Bean
//    public TokenFilter TokenFilter(){
//    	return new TokenFilter();
//    }
	
	@LoadBalanced    // Make sure to create the load-balanced template
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
