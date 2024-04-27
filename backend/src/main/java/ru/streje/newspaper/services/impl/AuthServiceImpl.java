package ru.streje.newspaper.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.InfoMessageResponse;
import ru.streje.newspaper.dtos.JwtRequest;
import ru.streje.newspaper.dtos.JwtResponse;
import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.services.AuthService;
import ru.streje.newspaper.services.UserService;
import ru.streje.newspaper.utilis.JwtTokenUtils;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserService userService;
	private final JwtTokenUtils jwtTokenUtils;
	private final AuthenticationManager authenticationManager;

	
	/**
	 * Метод получения токена авторизации
	 * 
	 * @param authRequest - параметры для получения токена
	 * 
	 * @return JwtResponse
	 */
	public JwtResponse createAuthToken(JwtRequest authRequest) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

		UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
		String firstName = userService.findByEmail(authRequest.getEmail()).get().getFirstname();
		String lastName = userService.findByEmail(authRequest.getEmail()).get().getLastname();
		String token = jwtTokenUtils.generateToken(userDetails, firstName, lastName);
		return new JwtResponse(token);
	}

	
	/**
	 * Метод регистрации нового пользователя
	 * 
	 * @param registrationUserRequest - параметры для рагистрации пользователя
	 * 
	 * @return InfoMessageResponse
	 */
	public InfoMessageResponse createNewUser(RegistrationUserRequest registrationUserRequest) {
		
		if (!registrationUserRequest.getPassword().equals(registrationUserRequest.getConfirmPassword())) {
			
			return new InfoMessageResponse(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают");
		}

		if (userService.findByEmail(registrationUserRequest.getEmail()).isPresent()) {
			return new InfoMessageResponse(HttpStatus.BAD_REQUEST.value(), "Пользователь с данной почтой уже существует");
		}

		userService.createNewUser(registrationUserRequest);
		return new InfoMessageResponse(HttpStatus.OK.value(), "Регистрация прошла успешно");
	}
	
	
	/**
	 * Метод проверки состояния токена
	 * 
	 * @param token - токен для проверки
	 * 
	 * @return InfoMessageResponse
	 */
	public InfoMessageResponse checkTokenStatus() {
		
		return new InfoMessageResponse(HttpStatus.OK.value(), "Токен активен");
	}
}