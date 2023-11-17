package ru.streje.newspaper.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationUserRequest {
	@NotBlank(message = "Поле firstname не может быть пустым и не может состоять только из пробелов")
	private String firstname;
	
	@NotBlank(message = "Поле lastname не может быть пустым и не может состоять только из пробелов")
	private String lastname;
	
	@NotBlank(message = "Поле password не может быть пустым и не может состоять только из пробелов")
	@Size(min = 10, message = "Пароль должен состоять минимум из 10 символов")
	private String password;
	
	private String confirmPassword;
	
	@NotBlank(message = "Поле email не может быть пустым и не может состоять только из пробелов")
	@Email(message = "Не соответствует формату почты")
	private String email;
}