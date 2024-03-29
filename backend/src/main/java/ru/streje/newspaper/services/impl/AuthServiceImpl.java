package ru.streje.newspaper.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.JwtRequest;
import ru.streje.newspaper.dtos.JwtResponse;
import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.messages.ErrorMessage;
import ru.streje.newspaper.messages.SuccesMessage;
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
	 * @return сообщение содержащее токен или сообщение о невалидности данных
	 */
	public ResponseEntity<?> createAuthToken(JwtRequest authRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(
					new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"),
					HttpStatus.UNAUTHORIZED);
		}

		UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
		String firstName = userService.findByEmail(authRequest.getEmail()).get().getFirstname();
		String lastName = userService.findByEmail(authRequest.getEmail()).get().getLastname();
		String token = jwtTokenUtils.generateToken(userDetails, firstName, lastName);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	/**
	 * Метод регистрации нового пользователя
	 * 
	 * @param registrationUserRequest - параметры для рагистрации пользователя
	 * @return сообщение о существовании пользователя с такими данными или сообщение
	 *         о успешности создания пользователя
	 */
	public ResponseEntity<?> createNewUser(RegistrationUserRequest registrationUserRequest) {
		if (!registrationUserRequest.getPassword().equals(registrationUserRequest.getConfirmPassword())) {
			return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"),
					HttpStatus.BAD_REQUEST);
		}

		if (userService.findByEmail(registrationUserRequest.getEmail()).isPresent()) {
			return new ResponseEntity<>(
					new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Пользователь с данной почтой уже существует"),
					HttpStatus.BAD_REQUEST);
		}

		userService.createNewUser(registrationUserRequest);
		return new ResponseEntity<>(new SuccesMessage("Регистрация прошла успешно"), HttpStatus.OK);
	}

	/**
	 * Метод проверки состояния токена
	 * 
	 * @param token - токен для проверки
	 * @return статус OK, если токен активен, иначе выкинет ошибку 401
	 */
	public ResponseEntity<?> checkTokenStatus(String token) {
		return new ResponseEntity<>("Токен активен", HttpStatus.OK);
	}
}