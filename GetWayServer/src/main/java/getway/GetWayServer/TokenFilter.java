package getway.GetWayServer;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class TokenFilter extends ZuulFilter {
	
//	@Autowired
//	private AuthClient authClient;
	
//	@Autowired        // NO LONGER auto-created by Spring Cloud (see below)
//    @LoadBalanced     // Explicitly request the load-balanced template // with Ribbon built-in
//	private RestTemplate client;

	@Override public String filterType() { 
		return "pre"; // 可以在請求被路由之前呼叫
	} 
	
	@Override public int filterOrder() { 
		return 0; // filter執行順序，通過數字指定 ,優先順序為0，數字越大，優先順序越低 
	}
	
	@Override public boolean shouldFilter() { 
		return true;// 是否執行該過濾器，此處為true，說明需要過濾 
	}
	
	@Override
	public Object run() {
		
		RequestContext context = RequestContext.getCurrentContext();
		
		HttpServletRequest req = context.getRequest();
		HttpServletResponse res = context.getResponse();
		
		CustomResourceOwnerPasswordResourceDetails  resource = new CustomResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri("http://localhost:9998/oauth/token");       
        resource.setClientId("clientId1");
        resource.setClientSecret("123qwe");
        resource.setScope(Arrays.asList("select"));
        resource.setUsername("tim");
        resource.setPassword("123qwe");
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource);
        
        OAuth2AccessToken accessToken = restTemplate.getAccessToken();
        System.out.println(accessToken.getValue());
		req.setAttribute("access_token", accessToken.getValue());
		
		try {
			req.getRequestDispatcher(req.getRequestURI() + "?access_token" + accessToken.getValue()).forward(req, res);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
//		System.out.println(restTemplate.getAccessToken().getValue());
        
        return null;
	}
	
	
	
	public static void main(String[] args){
		ResourceOwnerPasswordResourceDetails  resource = new ResourceOwnerPasswordResourceDetails ();
        resource.setAccessTokenUri("http://localhost:9998/oauth/token");       
        resource.setClientId("clientId1");
        resource.setClientSecret("123qwe");
        resource.setScope(Arrays.asList("select"));
        resource.setUsername("tim");
        resource.setPassword("123qwe");
        
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(atr));
        
        OAuth2AccessToken accessToken = restTemplate.getAccessToken();
        
        
        
        System.out.println(accessToken.getValue());
	}
	
	
	public class CustomResourceOwnerPasswordResourceDetails extends ResourceOwnerPasswordResourceDetails {
		@Override
		public boolean isClientOnly(){
			return true;
		}
	}
}
