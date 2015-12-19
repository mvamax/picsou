package io.picsou.security;

import io.picsou.domain.UserRole;
import io.picsou.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements
		org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		String lowerusername = username.toLowerCase();
		io.picsou.domain.User userFromDatabase = userRepository.findOneByUsername(lowerusername);
		List<GrantedAuthority> authorities = buildUserAuthority(userFromDatabase.getUserRole());
		return buildUserForAuthentication(userFromDatabase, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}
	private User buildUserForAuthentication(io.picsou.domain.User user, 
			List<GrantedAuthority> authorities) {
			return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
		}
}