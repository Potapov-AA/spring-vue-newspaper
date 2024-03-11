package ru.streje.newspaper.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.models.User;


public interface UserService extends UserDetailsService {
	
	public Optional<User> findByEmail(String email);
	
	public void createNewUser(RegistrationUserRequest registrationUserRequest);
}
