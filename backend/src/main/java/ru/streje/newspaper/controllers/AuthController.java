package ru.streje.newspaper.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.JwtRequest;
import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.services.AuthService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/auth")
	public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
		return authService.createAuthToken(authRequest);
	}

	@PostMapping("/registration")
	public ResponseEntity<?> createNewUser(@Valid @RequestBody RegistrationUserRequest registrationUserRequest) {
		return authService.createNewUser(registrationUserRequest);
	}

	@PostMapping("/checktoken")
	public ResponseEntity<?> checkTokenStatus(@RequestHeader("authorization") String token) {
		return authService.checkTokenStatus(token);
	}
}
