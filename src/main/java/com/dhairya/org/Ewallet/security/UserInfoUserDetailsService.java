package com.dhairya.org.Ewallet.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dhairya.org.Ewallet.entity.User;
import com.dhairya.org.Ewallet.repository.UserRepo;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = repo.findByMobileNumber(username);
		
		return user.map(UserInfoUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found"+ username));
		
	}

}
