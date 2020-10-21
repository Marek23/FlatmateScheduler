package pl.com.flat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.com.flat.security.IFacade;

@RestController
public class TestApi {
	@Autowired
	IFacade facade;

	@GetMapping("/currentuser")
	public String test() {
		return facade.currentUser();
	}
}
