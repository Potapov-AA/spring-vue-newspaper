package ru.streje.newspaper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.dtos.JwtRequest;
import ru.streje.newspaper.dtos.JwtResponse;
import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.services.AuthService;

@CrossOrigin
@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;

	
	@PostMapping("/auth")
	public JwtResponse createAuthToken(@RequestBody JwtRequest authRequest) {
		
		try {
	        return authService.createAuthToken(authRequest);
	    } catch (BadCredentialsException ex) {
	        throw new ResponseStatusException(
	          HttpStatus.UNAUTHORIZED, "Неправильный логин или пароль");
	    }
	}

	
	@PostMapping("/registration")
	public InfoMessageResponse createNewUser(@Valid @RequestBody RegistrationUserRequest registrationUserRequest) {
		
		return authService.createNewUser(registrationUserRequest);
	}

	
	@PostMapping("/checktoken")
	public InfoMessageResponse checkTokenStatus() {
		
		return authService.checkTokenStatus();
	}
}
