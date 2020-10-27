package pl.com.flat.security;

import org.springframework.security.core.Authentication;

import pl.com.flat.model.Resident;

public interface IFacade {
	Authentication getAuthentication();
	String currentUser();
	Resident currentResident();
}
