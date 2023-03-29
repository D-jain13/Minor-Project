package com.dhairya.org.Ewallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.org.Ewallet.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

	Optional<User> findByMobileNumber(String mobileNumber);
	
}
