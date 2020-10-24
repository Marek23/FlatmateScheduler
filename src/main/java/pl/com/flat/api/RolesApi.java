package pl.com.flat.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.com.flat.security.IFacade;

@RestController
public class RolesApi {
	@Autowired
	IFacade facade;

	@GetMapping("/roles")
	public Collection<?> roles() {
		return facade.getAuthentication().getAuthorities();
	}
}
