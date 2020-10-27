package pl.com.flat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import pl.com.flat.model.Resident;
import pl.com.flat.repository.ResidentRepository;

@Component
public class Facade implements IFacade {

	@Autowired
	ResidentRepository resRep;

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public String currentUser() {
		return getAuthentication().getName();
	}

	@Override
	public Resident currentResident() {
		return resRep.findByEmail(currentUser());
	}

}
