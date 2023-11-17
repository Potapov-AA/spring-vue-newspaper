package ru.streje.newspaper.dtos;

import lombok.Data;

@Data
public class RegistrationUserRequest {
	private String firstname;
	private String lastname;
	private String password;
	private String confirmPassword;
	private String email;
}