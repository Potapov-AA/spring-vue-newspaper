package ru.streje.newspaper.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.models.Role;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.services.UserService;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	@Test
	public void createNewUser_and_findThisUserByEmail_NotNull() {
		RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest();
		registrationUserRequest.setFirstname("FIRST NAME");
		registrationUserRequest.setLastname("LAST NAME");
		registrationUserRequest.setPassword("testtest");
		registrationUserRequest.setConfirmPassword("testtest");
		registrationUserRequest.setEmail("testUser@test.ru");
		
		userService.createNewUser(registrationUserRequest);
		
		User user = userService.findByEmail("testUser@test.ru").get();
		
		System.out.println(user);
		
		String userRole = null;
		for (Role role : user.getRoles()) {
			userRole = role.getName();
		}
		
		// Новый пользователь не должен быть пустым
		// У нового пользователя имя должно соответствовать "FIRST NAME"
		// У нового пользователя фамилия должна соответствовать "LAST NAME"
		// У нового пользователя почта должна соответствовать "test@test.ru"
		// У нового пользователя должна быть роль "ROLE_USER"
		assertEquals("FIRST NAME", user.getFirstname());
		assertEquals("LAST NAME", user.getLastname());
		assertEquals("testUser@test.ru", user.getEmail());
		assertEquals("ROLE_USER", userRole);
	}
}
