package com.soumya.taskproject.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soumya.taskproject.exception.UserNotFoundException;
import com.soumya.taskproject.model.Users;
import com.soumya.taskproject.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users users = userRepository.findByEmail(email).orElseThrow(
				() -> new UserNotFoundException(String.format("User with email : %d is not found", email)));
		Set<String> roles = new HashSet<>();
		roles.add("ROLE_ADMIN");
		return new User(users.getEmail(), users.getPassword(), userAuthorities(roles));
	}

	private Collection<? extends GrantedAuthority> userAuthorities(Set<String> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}

}

/*
 * private Collection<? extends GrantedAuthority> userAuthorities(Set<String>
 * roles) { return
 * roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
 * }
 */
