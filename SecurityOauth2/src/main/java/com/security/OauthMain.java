package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Oauth!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan
public class OauthMain 
{
	// url 
	//http://localhost:9999/oauth/token?grant_type=client_credentials&scope=select&client_id=tim&client_secret=timpass
    public static void main( String[] args ) {
    	SpringApplication.run(OauthMain.class, args);
    }
}
