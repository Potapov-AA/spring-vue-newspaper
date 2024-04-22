package ru.streje.newspaper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.dtos.JwtRequest;
import ru.streje.newspaper.dtos.JwtResponse;
import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.services.AuthService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

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
	public InfoMessageResponse checkTokenStatus(@RequestHeader("authorization") String token) {
		return authService.checkTokenStatus();
	}
}
