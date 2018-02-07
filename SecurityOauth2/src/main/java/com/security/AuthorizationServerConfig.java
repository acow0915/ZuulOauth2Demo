package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }


    /**用来定义授权，token终端和token服务器*/
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsServiceImpl)//若无，refresh_token会有UserDetailsService is required错误
                .tokenStore(tokenStore());
    }

    /**定义在token终端上的安全约束条件。*/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")//公开/oauth/token的接口
                .checkTokenAccess("isAuthenticated()");
    	//允许表单认证
    	//security.allowFormAuthenticationForClients();
    }

    /**定义ClientDetailsSercvice（客户端描述服务）的配置对象。Client details可以配初始化或者参考一个已经存在的存储。*/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	
    	//配置两个客户端,一个用于password认证一个用于client认证
    	clients.inMemory()
	        .withClient("clientId1")
	        .scopes("select")
	        .secret("123qwe")
	        .authorizedGrantTypes("password", "authorization_code", "refresh_token")
	        .accessTokenValiditySeconds(60)
	    .and()
	        .withClient("clientId2")
	        .scopes("xx")
	        .authorizedGrantTypes("implicit")
	        .accessTokenValiditySeconds(60);
    }

}
