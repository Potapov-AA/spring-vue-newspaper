package ru.streje.newspaper.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.streje.newspaper.dtos.RegistrationUserRequest;
import ru.streje.newspaper.models.Role;
import ru.streje.newspaper.models.User;
import ru.streje.newspaper.repositories.UserRepository;
import ru.streje.newspaper.services.RoleService;
import ru.streje.newspaper.services.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
				String.format("Пользователь с данной почтой '%s' не найден", email)));

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toList()));
	}
	
	
	/**
	 * Метод поиска пользователя по email
	 * 
	 * @param email - почта пользователя
	 * @return экземпляр User
	 */
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	/**
	 * Метод создания нового пользователя
	 * 
	 * @param registrationUserRequest - параметры для создания нового пользователя
	 */
	public void createNewUser(RegistrationUserRequest registrationUserRequest) {
		User user = new User();
		user.setEmail(registrationUserRequest.getEmail());
		user.setFirstname(registrationUserRequest.getFirstname());
		user.setLastname(registrationUserRequest.getLastname());
		user.setPassword(passwordEncoder.encode(registrationUserRequest.getPassword()));
		
		List<Role> roles = new ArrayList<>();
		roles.add(roleService.getUserRole());
		user.setRoles(roles);
		userRepository.save(user);
	}
}