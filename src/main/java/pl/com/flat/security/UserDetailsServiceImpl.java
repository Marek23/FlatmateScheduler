package pl.com.flat.security;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		var r = resRep.findByEmail(userName);
		if (r == null) throw new UsernameNotFoundException(userName);

		var auths = new ArrayList<SimpleGrantedAuthority>();
		
		r.getRoles().forEach(role -> {
			auths.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});

		return new User(r.getEmail(), r.getPassword(), auths);
	}
}
