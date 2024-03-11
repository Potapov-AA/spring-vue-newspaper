package ru.streje.newspaper.services;

import org.springframework.http.ResponseEntity;

import ru.streje.newspaper.dtos.JwtRequest;
import ru.streje.newspaper.dtos.RegistrationUserRequest;

public interface AuthService {
	
	public ResponseEntity<?> createAuthToken(JwtRequest authRequest);
	
	public ResponseEntity<?> createNewUser(RegistrationUserRequest registrationUserRequest);
	
	public ResponseEntity<?> checkTokenStatus(String token);
}
