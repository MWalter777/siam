package fia.ues.edu.siam.Services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fia.ues.edu.siam.entity.UserRole;
import fia.ues.edu.siam.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService{
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRespository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		fia.ues.edu.siam.entity.Users user = userRespository.findByUsername(username);
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserRole());
		return buildUser(user, authorities);
	}
	
	private User buildUser(fia.ues.edu.siam.entity.Users user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
	
	
	/*
	 * Funcion para construir nuestra lista de roles a validar con los usuarios
	 */
	private List<GrantedAuthority> buildAuthorities(Set<UserRole> userRole){
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for(UserRole roles : userRole) {
			authorities.add(new SimpleGrantedAuthority(roles.getRole()));
		}		
		return new ArrayList<GrantedAuthority>(authorities);
	}
	
	
	
	

}
