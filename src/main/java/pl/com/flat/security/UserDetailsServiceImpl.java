package pl.com.flat.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.com.flat.repository.ResidentRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	ResidentRepository resRep;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		var r = resRep.findByEmail(userName);
		if (r == null) throw new UsernameNotFoundException(userName);

		return new User(r.getEmail(), r.getPassword(), new ArrayList<>());
	}
}
