package oauth2.ResourceServer;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

	@RequestMapping("/access")
	@PreAuthorize("#oauth2.hasScope('select') or #oauth2.hasScope('read')")
	public String accessToken(){
		return "access done!";
	}
	
	@RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
