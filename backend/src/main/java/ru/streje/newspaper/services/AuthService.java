package ru.streje.newspaper.services;

import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.dtos.JwtRequest;
import ru.streje.newspaper.dtos.JwtResponse;
import ru.streje.newspaper.dtos.RegistrationUserRequest;

public interface AuthService {
	
	public JwtResponse createAuthToken(JwtRequest authRequest);
	
	public InfoMessageResponse createNewUser(RegistrationUserRequest registrationUserRequest);
	
	public InfoMessageResponse checkTokenStatus();
}
