package pl.com.flat.security;

import org.springframework.security.core.Authentication;

public interface IFacade {
	Authentication getAuthentication();
	String currentUser();
}